/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.buttons.ImageButton;
import org.pentaho.gwt.widgets.client.utils.ButtonHelper;
import org.pentaho.gwt.widgets.client.utils.ButtonHelper.ButtonLabelType;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.SimplePanel;

public class GwtButton extends AbstractGwtXulContainer implements XulButton {

  static final String ELEMENT_NAME = "button"; //$NON-NLS-1$

  enum DIRECTION {
    forward, reverse
  };

  enum ORIENT {
    horizontal, vertical
  }

  private String dir, group, image, type, onclick, tooltip, disabledImage;

  private enum Property {
    ID, CLASSNAME, LABEL, IMAGE, DISABLEDIMAGE, DISABLED
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtButton();
      }
    } );
  }

  private String className;

  private Button customButton;

  private Button button;

  private ImageButton imageButton;

  private boolean disabled;

  private MenuBar menuBar;

  private PopupPanel popupPanel;

  private HandlerRegistration handlerRegistration;

  public GwtButton() {
    super( ELEMENT_NAME );
    // programatic creation doesn't call init() create here for them
    button = new Button();
    button.setStylePrimaryName( "pentaho-button" );
    setManagedObject( button );
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {

        case CLASSNAME:
          this.setClassName( value );
          break;
        case LABEL:
          setLabel( value );
          break;
        case IMAGE:
          setImage( value );
          break;
        case DISABLEDIMAGE:
          setDisabledImage( value );
          break;
        case DISABLED:
          setDisabled( "true".equals( value ) );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {

    String classNameAttribute = srcEle.getAttribute( "pen:classname" );

    if ( !StringUtils.isEmpty( srcEle.getAttribute( "dir" ) ) && !StringUtils.isEmpty( srcEle.getAttribute( "image" ) )
        && !StringUtils.isEmpty( srcEle.getAttribute( "label" ) ) ) {

      if ( !StringUtils.isEmpty( srcEle.getAttribute( "pen:classname" ) ) ) {
        /*
         * customButton = new CustomButton(new Image(GWT.getModuleBaseURL() + srcEle.getAttribute("image")),
         * srcEle.getAttribute("label"),
         * getButtonLabelOrigin(srcEle.getAttribute("dir"),srcEle.getAttribute("orient"
         * )),,srcEle.getAttribute("pen:classname"));
         */
        customButton =
            new Button( ButtonHelper.createButtonLabel( new Image( GWT.getModuleBaseURL()
                + srcEle.getAttribute( "image" ) ), srcEle.getAttribute( "label" ), getButtonLabelOrigin( srcEle
                .getAttribute( "dir" ), srcEle.getAttribute( "orient" ) ), classNameAttribute ) );

      } else {
        /*
         * customButton = new CustomButton(new Image(GWT.getModuleBaseURL() + srcEle.getAttribute("image")),
         * srcEle.getAttribute("label"),
         * getButtonLabelOrigin(srcEle.getAttribute("dir"),srcEle.getAttribute("orient")));
         */
        customButton =
            new Button( ButtonHelper.createButtonLabel( new Image( GWT.getModuleBaseURL()
                + srcEle.getAttribute( "image" ) ), srcEle.getAttribute( "label" ), getButtonLabelOrigin( srcEle
                .getAttribute( "dir" ), srcEle.getAttribute( "orient" ) ) ) );
      }
      setManagedObject( customButton );
      button = null;

      if ( classNameAttribute != null && !classNameAttribute.isEmpty() ) {
        customButton.addStyleName( classNameAttribute );
      }

    } else if ( !StringUtils.isEmpty( srcEle.getAttribute( "image" ) ) ) {
      // we create a button by default, remove it here
      button = null;
      imageButton = new ImageButton();
      SimplePanel sp = new SimplePanel();
      setManagedObject( sp );
      sp.add( imageButton );
      imageButton.setHeight( "" );
      imageButton.setWidth( "" );

      if ( classNameAttribute != null && !classNameAttribute.isEmpty() ) {
        imageButton.addStyleName( classNameAttribute );
      }
    } else {
      button = new Button();
      button.setStylePrimaryName( "pentaho-button" );
      setManagedObject( button );

      if ( classNameAttribute != null && !classNameAttribute.isEmpty() ) {
        button.addStyleName( classNameAttribute );
      }
    }

    super.init( srcEle, container );
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "label" ) ) ) {
      setLabel( srcEle.getAttribute( "label" ) );
    }
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "onclick" ) ) ) {
      setOnclick( srcEle.getAttribute( "onclick" ) );
    }
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "image" ) ) ) {
      setImage( srcEle.getAttribute( "image" ) );
    }
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "tooltiptext" ) ) ) {
      setTooltiptext( srcEle.getAttribute( "tooltiptext" ) );
    }
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "pen:disabledimage" ) ) ) {
      this.setDisabledImage( srcEle.getAttribute( "pen:disabledimage" ) );
    }
    if ( !StringUtils.isEmpty( classNameAttribute ) ) {
      this.setClassName( srcEle.getAttribute( "pen:classname" ) );
    }

    setDisabled( "true".equals( srcEle.getAttribute( "disabled" ) ) );
  }

  public void setLabel( String label ) {
    if ( button != null ) {
      button.setHTML( "<span>" + label + "</span>" );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnclick( final String method ) {
    this.onclick = method;

    ClickHandler handler = new ClickHandler() {
      public void onClick( ClickEvent event ) {
        if ( !GwtButton.this.disabled ) {
          try {
            GwtButton.this.getXulDomContainer().invoke( method, new Object[] {} );
          } catch ( XulException e ) {
            e.printStackTrace();
          }
        }
      }
    };

    if ( button != null ) {
      handlerRegistration = button.addClickHandler( handler );
    } else if ( imageButton != null ) {
      handlerRegistration = imageButton.addClickHandler( handler );
    } else if ( customButton != null ) {
      handlerRegistration = customButton.addClickHandler( handler );
    }
  }

  private void setupClickHandler() {
    // Remove the handler that was added before

    if ( handlerRegistration != null ) {
      handlerRegistration.removeHandler();
    }

    // Add a new handler

    ClickHandler handler = new ClickHandler() {
      public void onClick( ClickEvent event ) {
        if ( !GwtButton.this.disabled ) {
          // Is this a GwtPopup Button and is this already created
          XulMenupopup popup = getPopupElement();
          if ( popup == null ) {
            try {
              GwtButton.this.getXulDomContainer().invoke( GwtButton.this.onclick, new Object[] {} );
            } catch ( XulException e ) {
              e.printStackTrace();
            }
          } else {
            popupPanel = new PopupPanel( true ) {

              @Override
              public boolean onKeyDownPreview( char key, int modifiers ) {
                // Use the popup's key preview hooks to close the dialog when either
                // enter or escape is pressed.
                switch ( key ) {
                  case KeyCodes.KEY_ESCAPE:
                    hide();
                    break;
                }
                return true;
              }

            };
            menuBar = new MenuBar( true );
            menuBar.setAutoOpen( true );
            // This is a GwtMenuPopop
            for ( XulComponent item : popup.getChildNodes() ) {
              if (item instanceof GwtMenuitem) {
                final GwtMenuitem tempItem = ( (GwtMenuitem) item );
                menuBar.addItem( tempItem.getLabel(), new PopupMenuCommand( tempItem.getCommand(), popupPanel ) );
              } else if (item instanceof GwtMenuSeparator) {
                final GwtMenuSeparator tempItem = ( (GwtMenuSeparator) item );
                menuBar.addSeparator( (MenuItemSeparator)tempItem.getManagedObject() );
              }
            }
            popupPanel.setWidget( menuBar );
            popupPanel.setPopupPositionAndShow( new PositionCallback() {
              public void setPosition( int offsetWidth, int offsetHeight ) {
                int absLeft = -1;
                int absTop = -1;
                int offHeight = -1;
                if ( button != null ) {
                  absLeft = button.getAbsoluteLeft();
                  absTop = button.getAbsoluteTop();
                  offHeight = button.getOffsetHeight();
                } else if ( imageButton != null ) {
                  absLeft = imageButton.getAbsoluteLeft();
                  absTop = imageButton.getAbsoluteTop();
                  offHeight = imageButton.getOffsetHeight();
                } else if ( customButton != null ) {
                  absLeft = customButton.getAbsoluteLeft();
                  absTop = customButton.getAbsoluteTop();
                  offHeight = customButton.getOffsetHeight();
                }
                popupPanel.setPopupPosition( absLeft, absTop + offHeight );
              }
            } );
          }
        }
      }
    };

    if ( button != null ) {
      handlerRegistration = button.addClickHandler( handler );
    } else if ( imageButton != null ) {
      handlerRegistration = imageButton.addClickHandler( handler );
    } else if ( customButton != null ) {
      handlerRegistration = customButton.addClickHandler( handler );
    }
  }

  @Bindable
  public String getLabel() {
    return ( button != null ) ? button.getText() : null;
  }

  @Bindable
  public boolean isDisabled() {
    return disabled;
  }

  @Bindable
  public void setDisabled( boolean dis ) {
    this.disabled = dis;
    if ( button != null ) {
      button.setEnabled( !dis );
      if ( dis ) {
        button.addStyleName( "disabled" );
      } else {
        button.removeStyleName( "disabled" );
      }
    } else if ( imageButton != null ) {
      imageButton.setEnabled( !dis );
    } else if ( customButton != null ) {
      customButton.setEnabled( !dis );
    }
  }

  public void doClick() {

    // button.click(); This was not working for me, TODO: investigate programatic click
    if ( onclick == null ) {
      return;
    }
    try {
      GwtButton.this.getXulDomContainer().invoke( onclick, new Object[] {} );
    } catch ( XulException e ) {
      e.printStackTrace();
    }
  }

  public String getDir() {
    return dir;
  }

  public String getGroup() {
    return group;
  }

  @Bindable
  public String getImage() {
    return image;
  }

  public String getOnclick() {
    return onclick;
  }

  public String getType() {
    return type;
  }

  @Bindable
  public boolean isSelected() {
    return false;
  }

  public void setDir( String dir ) {
    this.dir = dir;
  }

  public void setGroup( String group ) {
    this.group = group;
    // TODO: implement button group
  }

  @Bindable
  public void setImage( String src ) {
    if ( imageButton == null ) {
      button = null;
      imageButton = new ImageButton();
      SimplePanel sp = new SimplePanel();
      setManagedObject( sp );
      sp.add( imageButton );
      imageButton.setHeight( "" );
      imageButton.setWidth( "" );
    }
    if ( imageButton != null ) {
      src = GWT.getModuleBaseURL() + src;
      this.image = src;
      this.imageButton.setEnabledUrl( src );
      this.imageButton.setDisabledUrl( src );
    }
  }

  @Bindable
  public void setDisabledImage( String src ) {
    if ( imageButton != null ) {
      src = GWT.getModuleBaseURL() + src;
      this.disabledImage = src;
      this.imageButton.setDisabledUrl( src );
    }
  }

  @Bindable
  public void setSelected( String selected ) {
    button.setFocus( Boolean.parseBoolean( selected ) );
    // TODO: implement selected with button group;
  }

  @Bindable
  public void setSelected( boolean selected ) {
    if ( button != null ) {
      button.setFocus( selected );
    } else if ( imageButton != null ) {
      imageButton.setFocus( selected );
    } else if ( customButton != null ) {
      customButton.setFocus( selected );
    }
  }

  public void setType( String type ) {

    // TODO Auto-generated method stub

  }

  @Override
  @Bindable
  public void setTooltiptext( String tooltip ) {
    super.setTooltiptext( tooltip );
    if ( button != null ) {
      button.setTitle( tooltip );
    } else if ( imageButton != null ) {
      imageButton.setTitle( tooltip );
    } else if ( customButton != null ) {
      customButton.setTitle( tooltip );
    }

  }

  public void layout() {
    if ( imageButton != null ) {
      imageButton.setHeight( "" );
      imageButton.setWidth( "" );
    }

    if ( !initialized ) {
      setupClickHandler();
    }

    super.layout();

  }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );

    if ( button != null ) {
      button.setVisible( visible );
    } else if ( imageButton != null ) {
      imageButton.setVisible( visible );
    } else if ( customButton != null ) {
      customButton.setVisible( visible );
    }
  }

  private ButtonLabelType getButtonLabelOrigin( String direction, String orient ) {
    if ( direction == null || direction.length() <= 0 ) {
      direction = DIRECTION.forward.toString();
    }
    if ( orient == null || orient.length() <= 0 ) {
      orient = ORIENT.horizontal.toString();
    }

    if ( direction != null && orient != null ) {
      if ( direction.equals( DIRECTION.forward.toString() ) && orient.equals( ORIENT.vertical.toString() ) ) {
        return ButtonLabelType.TEXT_ON_BOTTOM;
      } else if ( direction.equals( DIRECTION.reverse.toString() ) && orient.equals( ORIENT.vertical.toString() ) ) {
        return ButtonLabelType.TEXT_ON_TOP;
      } else if ( direction.equals( DIRECTION.reverse.toString() ) && orient.equals( ORIENT.horizontal.toString() ) ) {
        return ButtonLabelType.TEXT_ON_LEFT;
      } else if ( direction.equals( DIRECTION.forward.toString() ) && orient.equals( ORIENT.horizontal.toString() ) ) {
        return ButtonLabelType.TEXT_ON_RIGHT;
      }
    }
    return ButtonLabelType.NO_TEXT;
  }

  public void setClassName( String className ) {
    this.className = className;
  }

  public String getClassName() {
    return className;
  }

  private GwtMenupopup getPopupElement() {
    for ( Element comp : getChildNodes() ) {
      if ( comp instanceof GwtMenupopup ) {
        return (GwtMenupopup) comp;
      }
    }
    return null;
  }

  class PopupMenuCommand implements Command {

    private String command;

    private PopupPanel popupPanel;

    public PopupMenuCommand( String command, PopupPanel popupPanel ) {
      this.command = command;
      this.popupPanel = popupPanel;
    }

    @Override
    public void execute() {
      try {
        if ( popupPanel != null ) {
          popupPanel.hide();
        }
        GwtButton.this.getXulDomContainer().invoke( command, new Object[] {} );
      } catch ( XulException e ) {
        System.out.println( "Error invoking method " + command + " " + e.getMessage() );
        e.printStackTrace();
      }
    }
  }
}
