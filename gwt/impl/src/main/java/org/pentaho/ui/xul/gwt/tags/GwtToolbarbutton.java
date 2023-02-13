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
 * Copyright (c) 2002-2023 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.toolbar.ToolbarButton;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarToggleButton;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulToolbarbutton;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class GwtToolbarbutton extends AbstractGwtXulComponent implements XulToolbarbutton {

  private ToolbarButton button;

  private String jscommand;

  private String dir, group, image, onclick, tooltip, disabledImage, type, downimage, downimagedisabled;

  private static final String DOM_NAME = "toolbarbutton";

  // WCAG default value for attribute 'alt' when image doesn't alternative text
  protected static final String DEFAULT_IMAGE_ALT_WCAG_TEXT = "";

  private enum Property {
    ID, LABEL, DISABLED, ONCLICK, IMAGE, TOOLTIPTEXT, VISIBLE, IMAGEALTTEXT
  }

  public GwtToolbarbutton() {
    this( null );
  }

  public GwtToolbarbutton( ToolbarButton toolbarButton) {
    this( DOM_NAME, toolbarButton );
  }

  public GwtToolbarbutton( String name, ToolbarButton toolbarButton ) {
    super( name );
    this.button = toolbarButton;
  }

  public static void register() {
    GwtXulParser.registerHandler( DOM_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbarbutton();
      }
    } );
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
        case DISABLED:
          setDisabled( "true".equals( value ) );
          break;
        case ONCLICK:
          setOnclick( value );
          break;
        case IMAGE:
          setImage( value );
          break;
        case TOOLTIPTEXT:
          setTooltiptext( value );
          break;
        case VISIBLE:
          setVisible( "true".equals( value ) );
          break;
        case IMAGEALTTEXT:
          setImageAltText( value );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  @Override
  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setType( srcEle.getAttribute( "type" ) );

    if ( this.type != null && this.type.equals( "toggle" ) ) {
      button = new ToolbarToggleButton( setImageDefaults( new Image() ) );
    } else {
      button = new ToolbarButton( setImageDefaults( new Image() ) );
    }
    setManagedObject( button );

    setLabel( srcEle.getAttribute( "label" ) );
    setOnclick( srcEle.getAttribute( "onclick" ) );
    setJscommand( srcEle.getAttribute( "js-command" ) );
    setImage( srcEle.getAttribute( "image" ) );
    setTooltiptext( srcEle.getAttribute( "tooltiptext" ) );
    setDisabled( "true".equals( srcEle.getAttribute( "disabled" ) ) );
    setDisabledImage( srcEle.getAttribute( "disabledimage" ) );
    setDownimage( srcEle.getAttribute( "downimage" ) );
    String vis = srcEle.getAttribute( "pen:visible" );
    if ( vis != null && !vis.equals( "" ) ) {
      setVisible( "true".equals( vis ) );
    }

    // Add class name to toolbar button
    String className = srcEle.getAttribute( "pen:classname" );
    if ( className != null && !className.isEmpty() ) {
      ( (ToolbarButton) getManagedObject() ).addClassName( className );
    }

    // first checks "pen:imagealttext" then other attributes
    initSetImageAltText( srcEle );
  }

  @Override
  public Object getManagedObject() {

    // HACK: ToolbarButtons are not Widget descendants...

    Object m = super.getManagedObject();
    if ( m == null ) {
      return m;
    }

    if ( ( !StringUtils.isEmpty( getId() ) && ( m instanceof ToolbarButton ) ) ) {
      ( (ToolbarButton) m ).setId( this.getId() );
    }
    return m;
  }

  public void doClick() {
    button.getCommand().execute();
  }

  @Bindable
  public void setLabel( String label ) {
    button.setText( label );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnclick( final String method ) {
    this.onclick = method;
    if ( method != null && button != null ) {
      button.setCommand( new Command() {
        public void execute() {
          try {
            GwtToolbarbutton.this.getXulDomContainer().invoke( method, new Object[] {} );
          } catch ( XulException e ) {
            e.printStackTrace();
          }
        }
      } );
    }
  }

  public String getJscommand() {
    return jscommand;
  }

  public void setJscommand( String jscommand ) {
    this.jscommand = jscommand;
    if ( jscommand != null && button != null ) {
      button.setCommand( new Command() {
        public void execute() {
          executeJS( GwtToolbarbutton.this.jscommand );
        }
      } );
    }
  }

  @Bindable
  public String getLabel() {
    return button.getText();
  }

  @Bindable
  public boolean isDisabled() {
    return !button.isEnabled();
  }

  @Bindable
  public void setDisabled( boolean dis ) {
    if ( button != null ) {
      button.setEnabled( !dis );
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

  /**
   * Retrieve image's alternative text.
   * @return
   */
  public String getImageAltText() {
    return ( button != null ) ? button.getImageAltText() : null;
  }

  public String getOnclick() {
    return onclick;
  }

  public String getType() {
    return type;
  }

  @Bindable
  public boolean isSelected() {
    if ( button instanceof ToolbarToggleButton ) {
      return ( (ToolbarToggleButton) button ).isSelected();
    }
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
    this.image = src;
    if ( button == null ) {
      return;
    }
    if ( src != null && src.length() > 0 ) {
      Image i = setImageDefaults( new Image( GWT.getModuleBaseURL() + src ) );
      // WebDriver support.. give the image a direct id we can use as a hook
      if ( !StringUtils.isEmpty( this.getId() ) ) {
        i.getElement().setId( this.getId().concat( "_img" ) );
      }
      button.setImage( i );
    }
  }

  /**
   * Set's the image's alternative text.
   * @param str
   */
  @Bindable
  public void setImageAltText( String str ) {
    if ( button != null ) {
      button.setImageAltText( str );
    }
  }

  /**
   * Setting miscellaneous attributes of image after it's image instantiation.
   * @param image
   * @return
   */
  protected Image setImageDefaults( Image image ) {
    return setDefaultImageAltTextIfNotSet( image );
  }

  /**
   * Set image's alternative text to {@link #DEFAULT_IMAGE_ALT_WCAG_TEXT} if originally undefined.
   * @param image
   * @return
   */
  protected Image setDefaultImageAltTextIfNotSet( Image image ) {
    if ( image != null && StringUtils.isEmpty( image.getAltText() ) ) {
      image.setAltText( DEFAULT_IMAGE_ALT_WCAG_TEXT );
    }
    return image;
  }

  /**
   * Convert property to internally used naming convention for attributes key.
   * @param property
   * @return
   */
  String toAttributeKey( Property property ) {
    return property.name().toLowerCase();
  }

  /**
   * Handles initialization of image's alt text.
   * Calls {@link #setImageAltText(String)} with value based on order of precedence:
   * <ol>
   *   <li>attribute {@link Property#IMAGEALTTEXT}'s name processed in
   *   {@link AbstractGwtXulComponent#init(com.google.gwt.xml.client.Element, XulDomContainer)}</li>
   *   <li>argument: {@code srcEle}'s attribute "pen:" + {@link Property#IMAGEALTTEXT} </li>
   *   <li>argument: {@code srcEle}'s attribute "tooltip"</li>
   *   <li> default value: {@link  #DEFAULT_IMAGE_ALT_WCAG_TEXT} </li>
   * </ol>
   * @param srcEle
   */
  void initSetImageAltText( com.google.gwt.xml.client.Element srcEle ) {
    List<String> texts = new ArrayList<>();
    // assuming super#init will have processed either "pen:imagealttext" or "imagealttext"
    texts.add( this.getAttributeValue( toAttributeKey( Property.IMAGEALTTEXT ) ) );
    texts.add( srcEle.getAttribute( "pen:imagealttext" ) ); // access from tag directly
    texts.add( srcEle.getAttribute( "tooltiptext" ) ); // fall back on "tooltiptext" to mirror hover over text

    String alternativeText = texts.stream()
        .filter( t -> !StringUtils.isEmpty( t ) )
        .findFirst()
        .orElse( DEFAULT_IMAGE_ALT_WCAG_TEXT );  // Per WCAG 2.1 all image tags must have 'alt' attribute
    setImageAltText( alternativeText );
  }

  @Bindable
  public void setDisabledImage( String src ) {
    this.disabledImage = src;
    if ( src != null && src.length() > 0 && button != null ) {
      button.setDisabledImage( setImageDefaults( new Image( GWT.getModuleBaseURL() + disabledImage ) ) );
    }
  }

  public String getDisabledImage() {
    return disabledImage;
  }

  public void setSelected( String selected ) {

  }

  @Bindable
  public void setSelected( boolean selected ) {
    if ( button instanceof ToolbarToggleButton ) {
      ( (ToolbarToggleButton) button ).setSelected( selected, false );
    }
  }

  @Bindable
  public void setSelected( boolean selected, boolean fireEvent ) {
    if ( button instanceof ToolbarToggleButton ) {
      ( (ToolbarToggleButton) button ).setSelected( selected, fireEvent );
    }
  }

  public void setType( String type ) {
    this.type = type;
  }

  @Override
  @Bindable
  public void setTooltiptext( String tooltip ) {
    super.setTooltiptext( tooltip );
    if ( button != null ) {
      button.setToolTip( tooltip );
    }
  }

  public String getDownimage() {
    return this.downimage;
  }

  public String getDownimagedisabled() {
    return this.downimagedisabled;
  }

  public void setDownimage( String img ) {
    this.downimage = img;
    if ( img != null && img.length() > 0 && button != null ) {
      button.setDownImage( setImageDefaults( new Image( GWT.getModuleBaseURL() + img ) ) );
    }
  }

  public void setDownimagedisabled( String img ) {
    this.downimagedisabled = img;
    if ( img != null && img.length() > 0 && button != null ) {
      button.setDownImageDisabled( setImageDefaults( new Image( GWT.getModuleBaseURL() + img ) ) );
    }
  }

  @Override
  @Bindable
  public void setVisible( boolean visible ) {
    super.setVisible( visible );
    if ( button != null ) {
      button.setVisible( visible );
    }
  }

}
