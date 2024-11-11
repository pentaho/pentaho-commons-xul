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


package org.pentaho.ui.xul.jface.tags;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.SwtXulUtil;
import org.pentaho.ui.xul.util.XulUtil;

public class JfaceMenuitem extends SwtElement implements XulMenuitem {
  private static int menuItemSerial = 0;
  private static final Log logger = LogFactory.getLog( JfaceMenuitem.class );
  private String onCommand;
  private boolean disabled = false;
  private XulDomContainer domContainer;
  private XulComponent parent;
  private String remainder;
  private ActionContributionItem contributionItem;
  private String acceltext = "";
  private String accesskey = "";
  private String label = "";
  private String image = "";
  private boolean selected = false;

  public JfaceMenuitem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    this( self, parent, domContainer, tagName, -1, null, parent.getManagedObject() != null
        && parent.getManagedObject() instanceof IMenuManager, false );
  }

  public JfaceMenuitem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName, int pos ) {
    this( self, parent, domContainer, tagName, pos, null, parent.getManagedObject() != null
        && parent.getManagedObject() instanceof IMenuManager, true );
  }

  public JfaceMenuitem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName, int pos,
      Action action ) {
    this( self, parent, domContainer, tagName, pos, action, true, true );
  }

  private JfaceMenuitem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName, int pos,
      Action action, boolean shouldCreate, boolean autoAdd ) {
    super( "menuitem" );
    this.parent = parent;
    this.domContainer = domContainer;
    if ( shouldCreate ) {
      createItem( self, parent, pos, autoAdd, action );
    }
  }

  public void setAction( Action action ) {
    contributionItem = new ActionContributionItem( action );
  }

  public IAction getAction() {
    return contributionItem.getAction();
  }

  private Action initAction( Element self, Action alternativeAction ) {
    int style = Action.AS_PUSH_BUTTON;
    if ( self != null && "checkbox".equals( self.getAttributeValue( "type" ) ) ) {
      style = Action.AS_CHECK_BOX;
    }

    Action action;
    if ( alternativeAction != null ) {
      action = alternativeAction;
    } else {
      action = new Action( ( self != null ) ? self.getAttributeValue( "label" ) : "tmp name", style ) {
        public void run() {
          String command = JfaceMenuitem.this.onCommand;
          if ( command != null ) {
            try {
              invoke( command );
            } catch ( Exception e ) {
              e.printStackTrace();
            }
          }
        }
      };
    }

    String id = getId();
    if ( id == null && self != null ) {
      id = self.getAttributeValue( "ID" );
    }
    if ( id == null ) {
      id = "menuitem-" + menuItemSerial;
      menuItemSerial++;
    }
    action.setId( id );

    action.setChecked( selected );
    return action;
  }

  public void createItem( Element self, XulComponent parent, int pos, boolean autoAdd ) {
    createItem( self, parent, pos, autoAdd, null );
  }

  public void createItem( Element self, XulComponent parent, int pos, boolean autoAdd, Action alternativeAction ) {
    contributionItem = new ActionContributionItem( initAction( self, alternativeAction ) );
    setManagedObject( contributionItem );
    if ( autoAdd ) {
      IMenuManager menu = (IMenuManager) parent.getManagedObject();
      if ( pos == -1 ) {
        menu.add( contributionItem );
      } else {
        if ( pos < getChildNodes().size() ) {
          String anchorId = menu.getItems()[pos].getId();
          if ( anchorId == null ) {
            menu.add( contributionItem );
            parent.addChild( this );
          } else {
            menu.insertBefore( anchorId, contributionItem );
            parent.addChildAt( this, pos );
          }
        } else {
          parent.addChild( this );
        }
      }
    }
  }

  public String getAcceltext() {
    return acceltext;
  }

  public String getAccesskey() {
    return accesskey;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public String getLabel() {
    return label;
  }

  public void setAcceltext( String accel ) {
    this.acceltext = accel;
    setText();
  }

  private void setText() {
    if ( contributionItem != null ) {
      String text = "";
      if ( this.label != null ) {
        text += this.label;
      }
      if ( !acceltext.isEmpty() ) {
        text += "\t" + acceltext;
      }

      contributionItem.getAction().setText( text );
    }
  }

  public void setAccesskey( String accessKey ) {
    if ( contributionItem != null ) {
      int mask = 0;
      if ( accessKey.indexOf( "ctrl" ) > -1 ) { //$NON-NLS-1$
        if( SwtXulUtil.isRunningOnWebspoonMode() ) {
          mask += SWT.CTRL;
        } else {
          boolean isMac = System.getProperty( "os.name" ).toLowerCase().indexOf( "mac" ) >= 0;
          mask += isMac ? SWT.COMMAND : SWT.CTRL;
        }
      }
      if ( accessKey.indexOf( "shift" ) > -1 ) { //$NON-NLS-1$
        mask += SWT.SHIFT;
      }
      if ( accessKey.indexOf( "alt" ) > -1 ) { //$NON-NLS-1$
        mask += SWT.ALT;
      }
      if ( accessKey.indexOf( "pageup" ) > -1 ) { //$NON-NLS-1$
        mask += SWT.PAGE_UP;
      }
      if ( accessKey.indexOf( "pagedown" ) > -1 ) { //$NON-NLS-1$
        mask += SWT.PAGE_DOWN;
      }
      if ( accessKey.indexOf( "home" ) > -1 ) { //$NON-NLS-1$
        mask += SWT.HOME;
      }
      if ( accessKey.indexOf( "esc" ) > -1 ) { //$NON-NLS-1$
        mask += SWT.ESC;
      }
      remainder =
          accessKey.replaceAll( "ctrl", "" ).replaceAll( "shift", "" ).replaceAll( "alt", "" ).replaceAll( "-", "" )
              .trim();
      if ( remainder.length() == 1 ) {
        mask += remainder.toUpperCase().charAt( 0 );
      } else if ( remainder.length() > 1 && remainder.startsWith( "f" ) ) { //$NON-NLS-1$
        // function key
        mask += LegacyActionTools.findKeyCode( remainder );
      }

      contributionItem.getAction().setAccelerator( mask );
      contributionItem.update( IAction.TEXT );
    }
  }

  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
    if ( contributionItem != null ) {
      contributionItem.getAction().setEnabled( !disabled );
    }
  }

  public void setLabel( String label ) {
    this.label = label;
    setText();
  }

  public String getImage() {
    return image;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected( boolean val ) {
    boolean changing = selected != val;
    selected = val;
    if ( contributionItem != null ) {
      contributionItem.getAction().setChecked( selected );
    }
    if ( parent != null && parent instanceof JfaceMenupopup && parent.getParent() instanceof JfaceMenuList ) {
      if ( val ) {
        ( (JfaceMenuList) parent.getParent() ).setSelectedItem( this );
      } else if ( changing ) {
        ( (JfaceMenuList) parent.getParent() ).setSelectedItem( null );
      }
    }
  }

  public void setImage( Image img ) {
    ImageDescriptor id = ImageDescriptor.createFromImage( img );

    if ( contributionItem != null ) {
      contributionItem.getAction().setImageDescriptor( id );
    }
  }

  public void setImage( String image ) {
    this.image = image;
    if ( StringUtils.isNotEmpty( image ) ) {
      InputStream in = null;
      try {
        in = XulUtil.loadResourceAsStream( image, domContainer );
        Image img = new Image( Display.getCurrent(), in );
        ImageDescriptor id = ImageDescriptor.createFromImage( img );

        if ( contributionItem != null ) {
          contributionItem.getAction().setImageDescriptor( id );
        }

      } catch ( FileNotFoundException e ) {
        logger.warn( e );
      } finally {
        try {
          if ( in != null ) {
            in.close();
          }
        } catch ( IOException ignored ) {
        }
      }
    }
  }

  public String getCommand() {
    return this.onCommand;
  }

  public void setCommand( final String command ) {
    this.onCommand = command;
  }

  public String toString() {
    return this.getLabel();
  }

  public void reposition( int position ) {
    contributionItem = null;
    createItem( this, parent, position, true );
    setDisabled( isDisabled() );
    this.setImage( getImage() );
    setSelected( isSelected() );
    setAcceltext( getAcceltext() );
  }

  @Override
  public void setVisible( boolean visible ) {
    this.visible = visible;
    contributionItem.setVisible( visible );
  }
}
