/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.ui.xul.swt;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Widget;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dnd.DataTransfer;
import org.pentaho.ui.xul.dnd.DropEffectType;
import org.pentaho.ui.xul.dnd.DropEvent;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.impl.AbstractXulComponent;
import org.pentaho.ui.xul.util.Orient;
import org.pentaho.ui.xul.util.SwtDragManager;

public class SwtElement extends AbstractXulComponent {
  private static final long serialVersionUID = -4407080035694005764L;

  private static final Log logger = LogFactory.getLog( SwtElement.class );

  // Per XUL spec, STRETCH is the default align value.
  private SwtAlign align = SwtAlign.STRETCH;

  protected Orient orient = Orient.HORIZONTAL;

  private int flex = 0;

  protected PropertyChangeSupport changeSupport = new PropertyChangeSupport( this );

  private boolean disabled;

  private static Map<String, Object> dndObjects = new WeakHashMap<String, Object>();
  private static Map<String, SwtElement> dndSources = new WeakHashMap<String, SwtElement>();

  public SwtElement( String tagName ) {
    super( tagName );
  }

  @Override
  public void addChild( Element e ) {
    super.addChild( e );

    if ( e instanceof XulContainer ) {
      AbstractSwtXulContainer container = (AbstractSwtXulContainer) e;
      if ( container.initialized == false ) {
        container.layout();
      }
    }

    XulComponent comp = (XulComponent) e;
    Object mo = comp.getManagedObject();
    if ( this instanceof XulRoot == false ) {
      if ( mo != null ) {
        if ( mo instanceof Control ) {
          if ( mo != getManagedObject()
            && getManagedObject() instanceof Composite
            && !( ( (Control) mo ).getParent() instanceof ScrolledComposite ) ) {
            ( (Control) mo ).setParent( (Composite) getManagedObject() );
          }
        } else if ( mo instanceof Viewer ) {
          if ( ( (Viewer) mo ).getControl() != getManagedObject() && getManagedObject() instanceof Composite ) {
            ( (Viewer) mo ).getControl().setParent( (Composite) getManagedObject() );
          }
        }
      }
    }
    // Only call a layout if one has already been done and the component being added will affect the UI
    if ( initialized && mo != null && mo instanceof String == false ) {

      layout();
      ( (XulComponent) e ).onDomReady();
    }

  }

  public void addChildAt( Element c, int pos ) {
    super.addChildAt( c, pos );
    if ( initialized ) {
      layout();
    }
  }

  @Override
  public void removeChild( Element ele ) {
    super.removeChild( ele );
    if ( ele instanceof XulComponent ) {
      XulComponent comp = (XulComponent) ele;
      if ( comp.getManagedObject() instanceof Widget ) {
        Widget thisWidget = (Widget) comp.getManagedObject();
        if ( thisWidget != null && !thisWidget.isDisposed() ) {
          thisWidget.dispose();
        }
      }
    }

    if ( initialized ) {
      layout();
    }
  }

  public int getFlex() {
    return flex;
  }

  public void setFlex( int flex ) {
    this.flex = flex;
  }

  public void setOrient( String orientation ) {
    orient = Orient.valueOf( orientation.toUpperCase() );
  }

  public String getOrient() {
    return orient.toString();
  }

  public Orient getOrientation() {
    return Orient.valueOf( getOrient() );
  }

  @Override
  public int getPadding() {
    return ( super.getPadding() > -1 ) ? super.getPadding() : 2;
  }

  @Override
  public int getSpacing() {
    return ( super.getSpacing() > -1 ) ? super.getSpacing() : 2;
  }

  @Override
  /**
   * This method attempts to follow the XUL rules
   * of layouting, using an SWT GridLayout. Any deviations 
   * from the general rules are applied in overrides to this method. 
   */
  public void layout() {
    super.layout();
    if ( this instanceof XulDeck ) {
      return;
    }

    if ( !( getManagedObject() instanceof Composite ) ) {
      return;
    }

    Composite container = (Composite) getManagedObject();

    // Total all flex values.
    // If every child has a flex value, then the GridLayout's columns
    // should be of equal width. everyChildIsFlexing gives us that boolean.

    int totalFlex = 0;
    int thisFlex = 0;
    boolean everyChildIsFlexing = true;

    for ( Object child : this.getChildNodes() ) {
      thisFlex = ( (SwtElement) child ).getFlex();
      if ( thisFlex <= 0 ) {
        everyChildIsFlexing = false;
      }
      totalFlex += thisFlex;
    }

    // By adding the total flex "points" with the number
    // of child controls, we get a close relative size, using
    // columns in the GridLayout.

    switch ( orient ) {
      case HORIZONTAL:
        int columnCount = this.getChildNodes().size() + totalFlex;
        GridLayout layout = new GridLayout( columnCount, everyChildIsFlexing );
        if ( this.getPadding() > -1 ) {
          layout.marginWidth = this.getPadding();
          layout.marginHeight = this.getPadding();
        }
        if ( this.getSpacing() > -1 ) {
          layout.horizontalSpacing = this.getSpacing();
          layout.verticalSpacing = this.getSpacing();
        }
        container.setLayout( layout );

        break;
      case VERTICAL:
        layout = new GridLayout();
        if ( this.getPadding() > -1 ) {
          layout.marginWidth = this.getPadding();
          layout.marginHeight = this.getPadding();
        }
        if ( this.getSpacing() > -1 ) {
          layout.horizontalSpacing = this.getSpacing();
          layout.verticalSpacing = this.getSpacing();
        }
        container.setLayout( layout );
        break;
    }

    for ( Object child : this.getChildNodes() ) {

      SwtElement swtChild = (SwtElement) child;

      // some children have no object they are managing... skip these kids!

      Object mo = swtChild.getManagedObject();
      if ( mo == null || !( mo instanceof Control || mo instanceof Viewer ) || swtChild instanceof XulDialog ) {
        continue;
      }

      Control c = null;
      if ( mo instanceof Control ) {
        c = (Control) mo;
      } else if ( mo instanceof Viewer ) {
        c = ( (Viewer) mo ).getControl();
      }

      GridData data = new GridData();

      // How many columns or rows should the control span? Use the flex value plus
      // 1 "point" for the child itself.
      data.horizontalSpan = orient.equals( Orient.HORIZONTAL ) ? swtChild.getFlex() + 1 : 1;
      data.verticalSpan = orient.equals( Orient.VERTICAL ) ? swtChild.getFlex() + 1 : 1;

      // In XUL, flex defines how the children grab the excess space
      // in the container - therefore, we need to grab the excess space...

      switch ( orient ) {
        case HORIZONTAL:
          data.verticalAlignment = SWT.FILL;
          data.grabExcessVerticalSpace = true;
          break;
        case VERTICAL:
          data.horizontalAlignment = SWT.FILL;
          data.grabExcessHorizontalSpace = true;
          break;
      }

      if ( swtChild.getFlex() > 0 ) {
        if ( swtChild.getWidth() == 0 ) {
          data.grabExcessHorizontalSpace = true;
          data.horizontalAlignment = SWT.FILL;
        }

        if ( swtChild.getHeight() == 0 ) {
          data.grabExcessVerticalSpace = true;
          data.verticalAlignment = SWT.FILL;
        }
      }

      if ( swtChild.getWidth() > 0 ) {
        data.widthHint = swtChild.getWidth();
      }

      if ( swtChild.getHeight() > 0 ) {
        data.heightHint = swtChild.getHeight();
      }

      // And finally, deal with the align attribute...
      // Align is the PARENT'S attribute, and affects the
      // opposite direction of the orientation.

      if ( ( (XulComponent) swtChild ).getAlign() != null ) {
        SwtAlign swtAlign = SwtAlign.valueOf( ( (XulContainer) swtChild ).getAlign().toString() );

        if ( orient.equals( Orient.HORIZONTAL ) ) {

          if ( swtChild.getHeight() < 0 ) {
            data.grabExcessVerticalSpace = true;
          }

        } else { // Orient.VERTICAL

          if ( swtChild.getWidth() < 0 ) {
            data.grabExcessHorizontalSpace = true;
          }
        }
      }
      if ( c.getLayoutData() instanceof FormData ) {
        int z = 0;
      }
      c.setLayoutData( data );
    }
    container.layout( true );
  }

  @Override
  /**
   * Important to understand that when using this method in the 
   * SWT implementation:
   * 
   * SWT adds new children positionally based on add order. This
   * means that a child that was added third in a list of 5 can't be 
   * "replaced" to its third position in the dialog, it can only 
   * be added to the end of the child list. 
   * 
   * Major SWT limitation. Replacement can only be used in 
   * a limited number of cases. 
   */
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {

    super.replaceChild( oldElement, newElement );
    Widget thisWidget = (Widget) oldElement.getManagedObject();
    if ( !thisWidget.isDisposed() ) {
      thisWidget.dispose();
    }
    ( (Control) newElement.getManagedObject() ).setParent( (Composite) this.getManagedObject() );

    layout();
  }

  public void setOnblur( String method ) {
    throw new NotImplementedException();
  }

  public void addPropertyChangeListener( PropertyChangeListener listener ) {
    changeSupport.addPropertyChangeListener( listener );
  }

  public void removePropertyChangeListener( PropertyChangeListener listener ) {
    changeSupport.removePropertyChangeListener( listener );
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
  }

  public void adoptAttributes( XulComponent component ) {
    throw new NotImplementedException();
  }

  public void setMenu( final IMenuManager menu ) {
    // the generic impl... override if you need a more sophisticated handling of the menu
    if ( getManagedObject() instanceof Control ) {
      final Control c = (Control) getManagedObject();
      c.addMouseListener( new MouseAdapter() {

        @Override
        public void mouseDown( MouseEvent evt ) {
          Control source = (Control) evt.getSource();
          Point pt = source.getDisplay().map( source, null, new Point( evt.x, evt.y ) );
          Menu m = ( (MenuManager) menu ).createContextMenu( source );
          m.setLocation( pt.x, pt.y );
          m.setVisible( true );
        }

      } );
    }
  }

  public void setPopup( IMenuManager menu ) {
    // the generic impl... override if you need a more sophisticated handling of the popupmenu
    if ( getManagedObject() instanceof Control ) {
      Control control = (Control) getManagedObject();
      if ( menu instanceof MenuManager ) {
        Menu m = ( (MenuManager) menu ).createContextMenu( control );
        menu.update( true );
      }
    }
  }

  @Override
  public void onDomReady() {
    super.onDomReady();
    if ( this.context != null ) {
      XulComponent pop = this.getDocument().getElementById( context );
      if ( pop == null ) {
        logger.error( "could not find popup menu (" + context + ") to add to this component" );
      } else {
        setPopup( (IMenuManager) pop.getManagedObject() );
      }
    }

    if ( this.menu != null ) {
      XulComponent pop = this.getDocument().getElementById( menu );
      if ( pop == null ) {
        logger.error( "could not find popup menu (" + context + ") to add to this component" );
      } else {
        setMenu( (IMenuManager) pop.getManagedObject() );
      }
    }
  }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );
    if ( getManagedObject() instanceof Control ) {
      Control control = (Control) getManagedObject();
      Object data = control.getLayoutData();
      if ( data instanceof GridData ) {
        ( (GridData) data ).exclude = !visible;
      }
      control.setLayoutData( data );
      control.setVisible( visible );
      control.getParent().layout( true );
    }
  }

  /**
   * this class is used internally as a way to keep track of the xultype, which is currently not exposed in the api but
   * could be used for grouping of drag and drop groups of widgets.
   */
  protected static class XulSwtDndType implements Serializable {

    private static final long serialVersionUID = 7356053006903234787L;

    String xultype;
    Object value;

    XulSwtDndType( String xultype, Object value, SwtElement xulSource ) {
      this.xultype = xultype;

      UUID uuid = UUID.randomUUID();
      dndObjects.put( uuid.toString(), value );
      dndSources.put( uuid.toString(), xulSource );
      this.value = uuid.toString();
    }

    public Object getValue() {
      return dndObjects.get( this.value );
    }

    public SwtElement getXulSource() {
      return dndSources.get( this.value );
    }
  }

  /**
   * this class is used for transferring strings and xul bound objects between swt xul elements. all elements being
   * transported are serialized and then deserialized.
   */
  private static class SwtDndTypeTransfer extends ByteArrayTransfer {

    private static final String TYPENAME = "xul-transfer"; //$NON-NLS-1$
    private static final int TYPEID = registerType( TYPENAME );
    private static SwtDndTypeTransfer _instance = new SwtDndTypeTransfer();

    private SwtDndTypeTransfer() {
    }

    public static SwtDndTypeTransfer getInstance() {
      return _instance;
    }

    protected String[] getTypeNames() {
      return new String[] { TYPENAME };
    }

    protected int[] getTypeIds() {
      return new int[] { TYPEID };
    }

    protected boolean validate( Object object ) {
      return object instanceof XulSwtDndType;
    }

    public void javaToNative( Object object, TransferData transferData ) {
      if ( object == null || !( object instanceof XulSwtDndType[] ) ) {
        return;
      }

      if ( isSupportedType( transferData ) ) {
        XulSwtDndType[] myTypes = (XulSwtDndType[]) object;
        try {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          ObjectOutputStream writeOut = new ObjectOutputStream( out );
          writeOut.writeInt( myTypes.length );
          for ( int i = 0; i < myTypes.length; i++ ) {
            writeOut.writeObject( myTypes[i] );
          }
          byte[] buffer = out.toByteArray();
          writeOut.close();
          super.javaToNative( buffer, transferData );
        } catch ( IOException e ) {
          e.printStackTrace();
        }
      }
    }

    public Object nativeToJava( TransferData transferData ) {
      if ( isSupportedType( transferData ) ) {
        byte[] buffer = (byte[]) super.nativeToJava( transferData );
        if ( buffer == null ) {
          return null;
        }
        ObjectInputStream readIn = null;
        try {
          readIn = new ObjectInputStream( new ByteArrayInputStream( buffer ) );
          int c = readIn.readInt();
          XulSwtDndType[] myData = new XulSwtDndType[c];
          for ( int i = 0; i < c; i++ ) {
            myData[i] = (XulSwtDndType) readIn.readObject();
          }
          readIn.close();
          return myData;
        } catch ( Exception ex ) {
          ex.printStackTrace();
          return null;
        } finally {
          if ( readIn != null ) {
            try {
              readIn.close();
            } catch ( IOException ignored ) {
            }
          }
        }
      }
      return null;
    }
  }

  /**
   * this call enables drag behavior for this element, it must be called by the component after the managed swt object
   * has been created
   */
  protected void enableDrag( final DropEffectType effect ) { // , final String xultype) {
    DragSource source = new DragSource( getDndObject(), lookupEffect( effect ) );
    Transfer[] types = new Transfer[] { SwtDndTypeTransfer.getInstance() };
    source.setTransfer( types );

    source.addDragListener( new DragSourceListener() {
      public void dragFinished( DragSourceEvent nativeEvent ) {
        // if (nativeEvent.doit) {
        // onSwtDragFinished(nativeEvent, lookupXulEffect(nativeEvent.detail));
        // }
      }

      public void dragSetData( DragSourceEvent nativeEvent ) {
        if ( SwtDndTypeTransfer.getInstance().isSupportedType( nativeEvent.dataType ) ) {
          // either strings or bindings, depending on whether elements are set
          List<Object> obj = getSwtDragData();
          XulSwtDndType[] types = new XulSwtDndType[obj.size()];
          for ( int i = 0; i < obj.size(); i++ ) {
            // note, the "xultype" concept is currently disabled and not exposed in the
            // public API. The idea here was to allow the drag and drop
            // components to specify their groupings in a single panel.

            types[i] = new XulSwtDndType( "xultype", obj.get( i ), SwtElement.this ); //$NON-NLS-1$
          }
          nativeEvent.data = types;
        }
      }

      public void dragStart( DragSourceEvent nativeEvent ) {
        DropEvent event = new DropEvent();
        DataTransfer dt = new DataTransfer();
        event.setDataTransfer( dt );
        dt.setData( getSwtDragData() );
        event.setAccepted( true );
        final String method = getOndrag();
        if ( method != null ) {
          try {
            Document doc = getDocument();
            XulRoot window = (XulRoot) doc.getRootElement();
            final XulDomContainer con = window.getXulDomContainer();
            con.invoke( method, new Object[] { event } );
          } catch ( XulException e ) {
            logger.error( "Error calling ondrop event: " + method, e ); //$NON-NLS-1$
          }
        }

        if ( !event.isAccepted() ) {
          nativeEvent.doit = false;
        }
        SwtDragManager.getInstance().setCurrentDropEvent( event );

      }
    } );
  }

  /**
   * the native dnd object may not be the managed object, so allow the child component to define it, defaulting to
   * managed object
   * 
   * @return dnd object
   */
  protected Control getDndObject() {
    return (Control) getManagedObject();
  }

  /**
   * called once the drag is finished
   * 
   * @param effect
   *          drop effect, used to detemine if removing is necessary
   * @param event
   */
  protected void onSwtDragFinished( DropEffectType effect, DropEvent event ) {
    throw new UnsupportedOperationException( "unsupported element type: " + getClass() ); //$NON-NLS-1$
  }

  /**
   * called to get drag data
   * 
   * @return list of draggable data, must be serializable
   */
  protected List<Object> getSwtDragData() {
    throw new UnsupportedOperationException( "unsupported element type: " + getClass() ); //$NON-NLS-1$
  }

  /**
   * this call enables drop behavior for this element. it must be called by the component after the managed swt object
   * has been created
   */
  protected void enableDrop() { // final String xultype) {

    DropTarget target = new DropTarget( getDndObject(), DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_DEFAULT );

    target.setTransfer( new Transfer[] { SwtDndTypeTransfer.getInstance() } );

    target.addDropListener( new DropTargetListener() {
      public void dragEnter( DropTargetEvent arg0 ) {
        arg0.detail = arg0.operations;
      }

      public void dragLeave( DropTargetEvent arg0 ) {
      }

      public void dragOperationChanged( DropTargetEvent arg0 ) {
      }

      public void dragOver( DropTargetEvent event ) {
        onSwtDragOver( event );
      }

      public void drop( DropTargetEvent nativeEvent ) {
        DropEvent event = new DropEvent();
        DataTransfer dataTransfer = new DataTransfer();
        XulSwtDndType[] types = (XulSwtDndType[]) nativeEvent.data;
        SwtElement xulDndSource = null;
        try {
          if ( types != null ) { // && types[0].xultype.equals(xultype)) {
            List<Object> objs = new ArrayList<Object>();
            for ( int i = 0; i < types.length; i++ ) {
              if ( i == 0 ) {
                xulDndSource = types[i].getXulSource();
              }
              objs.add( types[i].getValue() );

            }
            dataTransfer.setData( objs );
            dataTransfer.setDropEffect( lookupXulEffect( nativeEvent.detail ) );
          } else {
            nativeEvent.detail = DND.DROP_NONE;
            return;
          }
        } catch ( Exception e ) {
          e.printStackTrace();
        }
        event.setDataTransfer( dataTransfer );
        event.setAccepted( true );
        event.setNativeEvent( nativeEvent );

        resolveDndParentAndIndex( event );

        final String method = getOndrop();
        if ( method != null ) {
          try {
            Document doc = getDocument();
            XulRoot window = (XulRoot) doc.getRootElement();
            final XulDomContainer con = window.getXulDomContainer();
            con.invoke( method, new Object[] { event } );
          } catch ( XulException e ) {
            logger.error( "Error calling ondrop event: " + method, e ); //$NON-NLS-1$
          }
        }

        if ( !event.isAccepted() ) {
          nativeEvent.detail = DND.DROP_NONE;
          return;
        }

        // remove the item from the list
        if ( xulDndSource == null ) {
          throw new RuntimeException( "DND Source is null" );
        }
        xulDndSource.onSwtDragFinished( lookupXulEffect( nativeEvent.detail ), event );

        onSwtDragDropAccepted( event );

      }

      public void dropAccept( DropTargetEvent arg0 ) {
      }

    } );
  }

  /**
   * used by child classes on a drop event to determine the parent and index of the drop.
   * 
   * Currently only used by SwtTree in hierarchial mode.
   * 
   * @param event
   */
  protected void resolveDndParentAndIndex( DropEvent event ) {
    // not necessary, but is used by tree and list to specify the parent and index
  }

  /**
   * used by child classes on the drag over event in swt
   * 
   * @param nativeEvent
   *          the native swt event
   */
  protected void onSwtDragOver( DropTargetEvent nativeEvent ) {
    // not necessary, but used by tree and list to control types of DND behavior
  }

  /**
   * this method is called once the drop has been accepted
   * 
   * @param event
   *          the drop event
   */
  protected void onSwtDragDropAccepted( DropEvent event ) {
    throw new UnsupportedOperationException( "unsupported element type: " + getClass() ); //$NON-NLS-1$
  }

  /**
   * Maps a xul effect to SWT effect
   * 
   * @param effect
   *          xul effect
   * @return swt effect
   */
  private int lookupEffect( DropEffectType effect ) {
    switch ( effect ) {
      case NONE:
        return DND.DROP_NONE;
      case MOVE:
        return DND.DROP_MOVE;
      case LINK:
        return DND.DROP_LINK;
      case COPY:
      default:
        return DND.DROP_COPY;
    }
  }

  /**
   * maps a swt effect to a xul effect
   * 
   * @param effect
   *          swt effect
   * @return xul effect
   */
  private DropEffectType lookupXulEffect( int effect ) {
    switch ( effect ) {
      case DND.DROP_NONE:
        return DropEffectType.NONE;
      case DND.DROP_MOVE:
        return DropEffectType.MOVE;
      case DND.DROP_LINK:
        return DropEffectType.LINK;
      case DND.DROP_COPY:
      default:
        return DropEffectType.COPY;
    }
  }

  @Override
  public void setTooltiptext( String tooltip ) {
    super.setTooltiptext( tooltip );
    if ( getManagedObject() instanceof Control ) {
      ( (Control) getManagedObject() ).setToolTipText( tooltip );
    }
  }

}
