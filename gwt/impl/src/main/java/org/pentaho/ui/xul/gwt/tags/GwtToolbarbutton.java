/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


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
import org.pentaho.ui.xul.gwt.tags.util.ImageUtil;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;

public class GwtToolbarbutton extends AbstractGwtXulComponent implements XulToolbarbutton {

  private ToolbarButton button;

  private ImageUtil imageUtil;

  private String jscommand;

  private String dir;

  private String group;

  private String image;

  private String onclick;

  private String disabledImage;

  private String type;

  private String downimage;

  private String downimagedisabled;

  private static final String DOM_NAME = "toolbarbutton";

  private enum Property {
    ID, LABEL, DISABLED, ONCLICK, IMAGE, TOOLTIPTEXT, VISIBLE, IMAGEALTTEXT
  }

  public GwtToolbarbutton() {
    this( null );
  }

  public GwtToolbarbutton( ToolbarButton toolbarButton) {
    this( DOM_NAME, toolbarButton, new ImageUtil() );
  }

  public GwtToolbarbutton( String name, ToolbarButton toolbarButton, ImageUtil imageUtil ) {
    super( name );
    this.button = toolbarButton;
    this.imageUtil = imageUtil;
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
        default:
          // do nothing
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
      button = new ToolbarToggleButton( imageUtil.setImageDefaults( new Image() ) );
    } else {
      button = new ToolbarButton( imageUtil.setImageDefaults( new Image() ) );
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
  @Override
  public boolean isDisabled() {
    return !button.isEnabled();
  }

  @Bindable
  @Override
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
      Image i = imageUtil.setImageDefaults( new Image( GWT.getModuleBaseURL() + src ) );
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
   * Handles initialization of image's alt text.
   * @param srcEle
   */
  void initSetImageAltText( com.google.gwt.xml.client.Element srcEle ) {
    String alternativeText = imageUtil.getAltText( srcEle );
    setImageAltText( alternativeText );
  }

  @Bindable
  public void setDisabledImage( String src ) {
    this.disabledImage = src;
    if ( src != null && src.length() > 0 && button != null ) {
      button.setDisabledImage( imageUtil.setImageDefaults( new Image( GWT.getModuleBaseURL() + disabledImage ) ) );
    }
  }

  public String getDisabledImage() {
    return disabledImage;
  }

  public void setSelected( String selected ) {
    // NOTE empty on purpose
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
      button.setDownImage( imageUtil.setImageDefaults( new Image( GWT.getModuleBaseURL() + img ) ) );
    }
  }

  public void setDownimagedisabled( String img ) {
    this.downimagedisabled = img;
    if ( img != null && img.length() > 0 && button != null ) {
      button.setDownImageDisabled( imageUtil.setImageDefaults( new Image( GWT.getModuleBaseURL() + img ) ) );
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

  @Override
  protected void updateManagedClassName( String prevClassName ) {
    if ( button != null ) {
      button.addClassName( StringUtils.defaultString( getClassName(), "" ) );
    }
  }
}
