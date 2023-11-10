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
 * Copyright (c) 2002-2023 Hitachi Vantara. All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.buttons.ImageButton;
import org.pentaho.gwt.widgets.client.utils.ButtonHelper;
import org.pentaho.gwt.widgets.client.utils.ButtonHelper.ButtonLabelType;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.tags.util.ImageUtil;
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

import java.util.HashMap;
import java.util.Map;

public class GwtButton extends AbstractGwtXulContainer implements XulButton {

  static final String ELEMENT_NAME = "button";

  enum DIRECTION {
    forward, reverse
  }

  enum ORIENT {
    horizontal, vertical
  }

  private String dir, group, image, type, onclick, tooltip, disabledImage;

  private enum Property {
    ID, LABEL, IMAGE, DISABLEDIMAGE, DISABLED, IMAGEALTTEXT
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, GwtButton::new );
  }

  private Button customButton;

  private Button button;

  private ImageButton imageButton;

  private boolean disabled;

  private MenuBar menuBar;

  private PopupPanel popupPanel;

  private HandlerRegistration handlerRegistration;

  private Map<ClickHandler, HandlerRegistration> clickHandlerMap = new HashMap<>();

  private ImageUtil imageUtil;

  public GwtButton() {
    super( ELEMENT_NAME );
    // Programmatic creation doesn't call init() create here for them.
    button = new Button();
    button.setStylePrimaryName( "pentaho-button" );
    setManagedObject( button );
    imageUtil = new ImageUtil();
  }

  public GwtButton( Button customButton, Button button, ImageButton imageButton, ImageUtil imageUtil ) {
    super( ELEMENT_NAME );
    this.customButton = customButton;
    this.button = button;
    this.imageButton = imageButton;
    this.imageUtil = imageUtil;
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {

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
        case IMAGEALTTEXT:
          setImageAltText( value );
          break;
        default:
          // do nothing
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {


    if ( !StringUtils.isEmpty( srcEle.getAttribute( "dir" ) ) && !StringUtils.isEmpty( srcEle.getAttribute( "image" ) )
        && !StringUtils.isEmpty( srcEle.getAttribute( "label" ) ) ) {

      String classNameAttribute = StringUtils.defaultIfEmpty( srcEle.getAttribute( "pen:classname" ), null );
      customButton =
              new Button( ButtonHelper.createButtonLabel(
                      new Image( GWT.getModuleBaseURL() + srcEle.getAttribute( "image" ) ),
                      srcEle.getAttribute( "label" ),
                      getButtonLabelOrigin( srcEle.getAttribute( "dir" ), srcEle.getAttribute( "orient" ) ),
                      classNameAttribute ) );
      button = null;
      setManagedObject( customButton );

    } else if ( !StringUtils.isEmpty( srcEle.getAttribute( "image" ) ) ) {
      // we create a button by default, remove it here
      button = null;
      initImageButton();
    } else {
      button = new Button();
      button.setStylePrimaryName( "pentaho-button" );
      setManagedObject( button );
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

    setDisabled( "true".equals( srcEle.getAttribute( "disabled" ) ) );

    if ( imageButton != null ) {
      initSetImageAltText( srcEle );
    }
  }

  /**
   * Handles initialization of image's alt text.
   * @param srcEle
   */
  void initSetImageAltText( com.google.gwt.xml.client.Element srcEle ) {
    String alternativeText = imageUtil.getAltText( srcEle );
    setImageAltText( alternativeText );
  }

  /**
   * Create a new ImageButton instance.
   * @return
   */
  private ImageButton initImageButton() {
    imageButton = new ImageButton();
    SimplePanel sp = new SimplePanel();
    setManagedObject( sp );
    sp.add( imageButton );

    imageButton.addStyleName( "icon-zoomable" );
    imageButton.setHeight( "" );
    imageButton.setWidth( "" );
    imageUtil.setImageDefaults( imageButton );

    return imageButton;
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

    if ( handlerRegistration != null ) {
      handlerRegistration.removeHandler();
    }

    handlerRegistration = addClickHandlerCore( handler );
  }

  public HandlerRegistration addClickHandler( ClickHandler handler ) {

    clickHandlerMap.put( handler, addClickHandlerCore( handler ) );

    return () -> {
      removeClickHandlerCore( handler );
    };
  }

  private HandlerRegistration addClickHandlerCore( ClickHandler handler ) {
    Widget managedButton = getManagedButton();
    if ( managedButton != null ) {
      return ( (HasClickHandlers) managedButton ).addClickHandler( handler );
    }

    return null;
  }

  private void removeClickHandlerCore( ClickHandler handler ) {
    HandlerRegistration hr = clickHandlerMap.get( handler );
    if ( hr != null ) {
      hr.removeHandler();
    }

    clickHandlerMap.remove( handler );
  }

  private void setupClickHandler() {
    // Remove the handler that was added before

    if ( handlerRegistration != null ) {
      handlerRegistration.removeHandler();
    }

    for ( Map.Entry<ClickHandler, HandlerRegistration> entry : clickHandlerMap.entrySet() ) {
      HandlerRegistration hr = entry.getValue();
      if ( hr != null ) {
        hr.removeHandler();
      }

      entry.setValue( null );
    }

    if ( getPopupElement() != null ) {
      imageButton.getElement().setAttribute( "aria-haspopup", "menu" );
      imageButton.getElement().setAttribute( "aria-expanded", "false" );
    }

    // Add a new handler

    ClickHandler handler = new ClickHandler() {
      public void onClick( ClickEvent event ) {
        if ( !GwtButton.this.disabled ) {
          // Is this a GwtPopup Button and is this already created
          XulMenupopup popup = getPopupElement();
          if ( popup == null ) {
            if ( GwtButton.this.onclick != null ) {
              try {
                GwtButton.this.getXulDomContainer().invoke( GwtButton.this.onclick, new Object[] {} );
              } catch ( XulException e ) {
                e.printStackTrace();
              }
            }
          } else {
            popupPanel = new PopupPanel( true ) {

              @Override
              public boolean onKeyDownPreview( char key, int modifiers ) {
                // Use the popup's key preview hooks to close the dialog when either
                // enter or escape is pressed.
                switch ( key ) {
                  case KeyCodes.KEY_ESCAPE:
                  case KeyCodes.KEY_TAB: {
                    this.hide();
                    setSelected( true );
                    break;
                  }
                }
                return true;
              }

            };
            menuBar = new MenuBar( true );
            menuBar.setAutoOpen( true );
            menuBar.getElement().setId( DOM.createUniqueId() );
            imageButton.getElement().setAttribute( "aria-controls", menuBar.getElement().getId() );
            imageButton.getElement().setAttribute( "aria-expanded", "true" );

            popupPanel.addCloseHandler( ev -> {
              imageButton.getElement().removeAttribute( "aria-controls" );
              imageButton.getElement().setAttribute( "aria-expanded", "false" );
            } );

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
            menuBar.focus();
          }
        }
      }
    };

    handlerRegistration = addClickHandlerCore( handler );

    for ( Map.Entry<ClickHandler, HandlerRegistration> entry : clickHandlerMap.entrySet() ) {
      entry.setValue( addClickHandlerCore( entry.getKey() ) );
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

  /**
   * Set the image's alternative text.
   * @param str
   */
  @Bindable
  public void setImageAltText( String str ) {
    if ( imageButton != null ) {
      imageButton.setAltText( str );
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

  /**
   * Retrieve image's alternative text.
   * @return
   */
  public String getImageAltText() {
    return ( imageButton != null ) ? imageButton.getAltText() : null;
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
      imageButton = initImageButton();
    } else {
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
    setSelected( Boolean.parseBoolean( selected ) );
  }

  @Bindable
  public void setSelected( boolean selected ) {
    Widget managedButton = getManagedButton();
    if ( managedButton != null ) {
      ( (Focusable) managedButton ).setFocus( selected );
    }
  }

  public void setType( String type ) {
    // noop
  }

  @Override
  @Bindable
  public void setTooltiptext( String tooltip ) {
    super.setTooltiptext( tooltip );

    Widget managedButton = getManagedButton();
    if ( managedButton != null ) {
      managedButton.setTitle( tooltip );
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

  private Widget getManagedButton() {
    if ( button != null ) {
      return button;
    }

    if ( imageButton != null ) {
      return imageButton;
    }

    if ( customButton != null ) {
      return customButton;
    }

    return null;
  }

  @Override
  protected Object getManagedClassNameObject() {
    return getManagedButton();
  }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );

    Widget managedButton = getManagedButton();
    if ( managedButton != null ) {
      managedButton.setVisible( visible );
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

        if ( button != null ) {
          ElementUtils.focusSync( button.getElement() );
        } else if ( imageButton != null ) {
          ElementUtils.focusSync( imageButton.getElement() );
        } else if ( customButton != null ) {
          ElementUtils.focusSync( customButton.getElement() );
        }

        GwtButton.this.getXulDomContainer().invoke( command, new Object[] {} );
      } catch ( XulException e ) {
        System.out.println( "Error invoking method " + command + " " + e.getMessage() );
        e.printStackTrace();
      }
    }
  }
}
