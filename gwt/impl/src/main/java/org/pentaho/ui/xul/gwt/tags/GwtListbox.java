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

package org.pentaho.ui.xul.gwt.tags;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.listbox.CustomListBox;
import org.pentaho.gwt.widgets.client.listbox.DefaultListItem;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.binding.GwtBindingContext;
import org.pentaho.ui.xul.gwt.binding.GwtBindingMethod;
import org.pentaho.ui.xul.gwt.util.XulDragController;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

public class GwtListbox extends AbstractGwtXulContainer implements XulListbox, ChangeHandler {

  static final String ELEMENT_NAME = "listbox"; //$NON-NLS-1$
  private int selectedIndex = -1;
  private Object[] previousSelectedItems;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtListbox();
      }
    } );
  }

  private enum Property {
    ID, SELECTEDINDEX
  }

  private Collection boundElements;

  private boolean disabled = false;

  private String selType = "SINGLE";

  private int rowsToDisplay = 0;

  private String onselect;

  private String binding;

  private XulDomContainer container;
  private Object prevSelecteItem;
  private String command;

  private CustomListBox listBox = new CustomListBox();
  private boolean loaded;
  private Object previousSelectedItem;
  private boolean suppressEvents;

  public GwtListbox() {
    super( ELEMENT_NAME );
    setManagedObject( listBox );

    listBox.addChangeListener( new ChangeListener() {

      public void onChange( Widget arg0 ) {

        /*
         * This actionlistener is fired at parse time when elements are added. We'll ignore that call by checking a
         * variable set to true post parse time
         */
        if ( !loaded ) {
          return;
        }

        fireSelectedEvents();
      }

    } );
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {
        case SELECTEDINDEX:
          setSelectedIndex( Integer.valueOf( value ) );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    this.container = container;
    setBinding( srcEle.getAttribute( "pen:binding" ) ); //$NON-NLS-1$
    setRows( 2 );
    if ( srcEle.hasAttribute( "width" ) && srcEle.getAttribute( "width" ).trim().length() > 0 ) { //$NON-NLS-1$ //$NON-NLS-2$
      setWidth( Integer.parseInt( srcEle.getAttribute( "width" ) ) ); //$NON-NLS-1$
    }
    if ( srcEle.hasAttribute( "rows" ) && srcEle.getAttribute( "rows" ).trim().length() > 0 ) { //$NON-NLS-1$ //$NON-NLS-2$
      setRows( Integer.parseInt( srcEle.getAttribute( "rows" ) ) ); //$NON-NLS-1$
    }

    if ( srcEle.hasAttribute( "pen:ondrag" ) && srcEle.getAttribute( "pen:ondrag" ).trim().length() > 0 ) {
      setOndrag( srcEle.getAttribute( "pen:ondrag" ) );
    }

    if ( StringUtils.isEmpty( srcEle.getAttribute( "seltype" ) ) == false ) {
      setSeltype( srcEle.getAttribute( "seltype" ) );
    }
    setOnselect( srcEle.getAttribute( "onselect" ) );
  }

  private void fireSelectedEvents() {
    if ( suppressEvents ) {
      return;
    }

    Object[] newSelectedObjects = getSelectedItems();
    firePropertyChange( "selectedItem", previousSelectedItem, getSelectedItem() );
    if ( !Arrays.equals( previousSelectedItems, newSelectedObjects ) ) {
      previousSelectedItems = newSelectedObjects;
      firePropertyChange( "selectedItems", null, newSelectedObjects );
    }
    int prevSelectedIndex = selectedIndex;
    selectedIndex = getSelectedIndex();
    firePropertyChange( "selectedIndex", prevSelectedIndex, selectedIndex );

    if ( StringUtils.isEmpty( getOnselect() ) == false && prevSelectedIndex != selectedIndex ) {
      try {
        getXulDomContainer().invoke( getOnselect(), new Object[] {} );
      } catch ( XulException e ) {
        e.printStackTrace();
      }
    }
  }

  @Bindable
  public boolean isDisabled() {
    return disabled;
  }

  @Bindable
  public void setDisabled( boolean disabled ) {
    this.listBox.setEnabled( !disabled );
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows( int rowsToDisplay ) {
    this.rowsToDisplay = rowsToDisplay;
    this.listBox.setVisibleRowCount( rowsToDisplay );
  }

  public String getSeltype() {
    return selType;
  }

  public void setSeltype( String selType ) {
    this.selType = selType;

  }

  public Orient getOrientation() {
    return null;
  }

  public void layout() {
    if ( suppressEvents ) {
      return;
    }
    if ( getWidth() > 0 ) {
      this.listBox.setWidth( getWidth() + "px" );
    } else {
      this.listBox.setWidth( "100%" );
    }
    this.listBox.setHeight( "100%" );
    listBox.setSuppressLayout( true );
    listBox.clear();
    listBox.setMultiSelect( selType.equalsIgnoreCase( "MULTI" ) );
    List<XulComponent> children = getChildNodes();
    for ( int i = 0; i < children.size(); i++ ) {
      if ( children.get( i ) instanceof GwtListitem ) {
        GwtListitem item = (GwtListitem) children.get( i );
        DefaultListItem li = new DefaultListItem( item.getLabel() );
        li.setBackingObject( ( boundElements != null ) ? boundElements.toArray()[i] : item );

        listBox.addItem( li );
      }
    }
    if ( getHeight() > 0 ) {
      listBox.setHeight( getHeight() + "px" );
    }
    if ( getWidth() > 0 ) {
      listBox.setWidth( getWidth() + "px" );
    }
    listBox.setSuppressLayout( false );
    setSelectedIndex( getSelectedIndex() );

    loaded = true;
  }

  public String getOnselect() {
    return onselect;
  }

  public void setOnselect( String onchange ) {
    this.onselect = onchange;
  }

  public void onChange( ChangeEvent changeEvent ) {
    if ( suppressEvents ) {
      return;
    }
    try {
      if ( onselect != null && onselect.length() > 0 ) {
        this.getXulDomContainer().invoke( onselect, new Object[] { new Integer( listBox.getSelectedIndex() ) } );
      }
    } catch ( XulException e ) {
      e.printStackTrace();
    }
    fireSelectedEvents();
    this.setSelectedIndex( listBox.getSelectedIndex() );
  }

  @Bindable
  public Object getSelectedItem() {
    if ( listBox.getSelectedIndex() != -1 ) {
      return getItem( listBox.getSelectedIndex() );
    } else {
      return null;
    }
  }

  private Object getItem( int index ) {
    if ( this.boundElements != null ) {
      // Bound situation, return domain object
      return boundElements.toArray()[index];
    }
    return index >= 0 ? getChildNodes().get( index ) : null;
  }

  @Bindable
  public Object[] getSelectedItems() {
    int[] indices = listBox.getSelectedIndices();
    Object[] selectedItems = new Object[indices.length];
    for ( int i = 0; i < indices.length; i++ ) {
      if ( boundElements != null ) {
        if ( indices[i] >= 0 && indices[i] < boundElements.size() ) {
          selectedItems[i] = getItem( indices[i] );
        } else {
          break;
        }
      } else {
        if ( indices[i] >= 0 && indices[i] < listBox.getItems().size() ) {
          selectedItems[i] = listBox.getItems().get( indices[i] ).getText();
        }
      }
    }

    return selectedItems;
  }

  private int getItemIndex( Object item ) {
    if ( item instanceof GwtListitem ) {
      GwtListitem listitem = (GwtListitem) item;
      for ( int i = 0; i < listBox.getSize(); i++ ) {
        GwtListitem currItem = (GwtListitem) getItem( i );
        if ( currItem.getLabel().equals( listitem.getLabel() ) ) {
          return i;
        }
      }
    } else {
      for ( int i = 0; i < listBox.getSize(); i++ ) {
        GwtListitem currItem = (GwtListitem) getItem( i );
        if ( currItem.getLabel().equals( item.toString() ) ) {
          return i;
        }
      }

    }
    return -1;
  }

  @Bindable
  public void setSelectedItem( Object item ) {
    if ( item == null ) {
      listBox.setSelectedIndex( -1 );
    } else {
      listBox.setSelectedIndex( getItemIndex( item ) );
    }
  }

  @Bindable
  public void setSelectedItems( Object[] items ) {
    List<?> list = Arrays.asList( this.boundElements.toArray() );
    int[] selIndices = new int[items.length];
    for ( int i = 0; i < items.length; i++ ) {
      if ( i < list.size() ) {
        selIndices[i] = list.indexOf( items[i] );
      } else {
        break;
      }
    }
    listBox.setSelectedIndices( selIndices );
  }

  public void addItem( Object item ) {

    // these need to stay in sync with the dom!

    try {
      GwtListitem itemobj = (GwtListitem) getXulDomContainer().getDocumentRoot().createElement( "listitem" );
      itemobj.setLabel( item.toString() );
      this.addChild( itemobj );
      if ( listBox != null ) {
        listBox.addItem( item.toString() );
      }

    } catch ( XulException e ) {
      // TODO: log or pass exception
    }
  }

  public void removeItems() {
    listBox.clear();
    for ( XulComponent c : this.getChildNodes() ) {
      this.removeChild( c );
    }
  }

  public int getRowCount() {
    return listBox.getSize();
  }

  @Bindable
  public int getSelectedIndex() {
    return listBox.getSelectedIndex();
  }

  @Bindable
  public int[] getSelectedIndices() {
    // TODO: customListbox doesn't seem to support multi-selection.
    return new int[] { getSelectedIndex() };
  }

  @Bindable
  public void setDisabled( String dis ) {
    this.disabled = Boolean.parseBoolean( dis );
  }

  @Bindable
  public <T> Collection<T> getElements() {
    return (Collection<T>) boundElements;
  }

  @Override
  public void removeChild( Element element ) {
    super.removeChild( element );
    layout();
  }

  @Bindable
  public <T> void setElements( Collection<T> elements ) {
    boundElements = elements;
    selectedIndex = -1;
    suppressEvents = true;
    for ( XulComponent child : this.getChildNodes() ) {
      this.removeChild( child );
    }
    boundElements = elements;
    for ( T t : elements ) {
      String attribute = getBinding();
      String label = null;
      if ( attribute != null && attribute.length() > 0 ) {
        label = extractLabel( t );
      } else {
        label = t.toString();
      }
      try {
        GwtListitem item = (GwtListitem) container.getDocumentRoot().createElement( "listitem" );
        item.setLabel( label );
        this.addChild( item );
      } catch ( XulException e ) {
        //ignored
      }

    }
    suppressEvents = false;
    layout();
  }

  @Bindable
  public void setSelectedIndex( int index ) {
    int oldValue = this.selectedIndex;
    this.selectedIndex = index;
    this.listBox.setSelectedIndex( index );

    if ( suppressEvents ) {
      return;
    }

    // TODO: move this all to the centralized fireSelectedEvents method
    this.firePropertyChange( "selectedIndex", oldValue, index );
    if ( getSelectedItem() != null ) {
      firePropertyChange( "selectedItems", new Object[] { prevSelecteItem }, new Object[] { getSelectedItem() } );
    } else {
      firePropertyChange( "selectedItems", new Object[] { prevSelecteItem }, new Object[] {} );
    }
    prevSelecteItem = getSelectedItem();

  }

  @Bindable
  public void setSelectedIndices( int[] indices ) {

    // TODO Auto-generated method stub

  }

  public String getBinding() {
    return binding;
  }

  public void setBinding( String binding ) {
    this.binding = binding;
  }

  private <T> String extractLabel( T t ) {
    String attribute = getBinding();
    if ( StringUtils.isEmpty( attribute ) ) {
      return t.toString();
    } else {
      try {
        GwtBindingMethod m = GwtBindingContext.typeController.findGetMethod( t, attribute );
        if ( m == null ) {

          return t.toString();

        }
        return m.invoke( t, new Object[] {} ).toString();

      } catch ( Exception e ) {
        throw new RuntimeException( e );
      }
    }
  }

  public void setWidth( int width ) {
    super.setWidth( width );
    listBox.setWidth( width + "px" );
  }

  @Override
  public void setHeight( int height ) {
    super.setHeight( height );
    listBox.setHeight( height + "px" );
  }

  public String getCommand() {
    return command;
  }

  public Widget makeProxy( Widget ele ) {
    return listBox.createProxy();
  }

  public void setCommand( final String command ) {
    this.command = command;
    listBox.setCommand( new Command() {
      public void execute() {

      }
    } );
  }

  @Override
  public void setOndrag( String ondrag ) {
    super.setOndrag( ondrag );

    listBox.setDragController( XulDragController.getInstance() );
  }

  @Override
  public String getOndrag() {
    return super.getOndrag();
  }

  public Object getDragObject() {
    return getSelectedItem();
  }
}
