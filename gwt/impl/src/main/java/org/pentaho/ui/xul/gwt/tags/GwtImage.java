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

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.tags.util.ImageUtil;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;

public class GwtImage extends AbstractGwtXulComponent implements XulImage {

  static final String ELEMENT_NAME = "image"; //$NON-NLS-1$

  private enum Property {
    ID, DISABLED, SRC, IMAGEALTTEXT
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtImage();
      }
    } );
  }

  private Image image;

  private ImageUtil imageUtil;

  private String onclick;

  public GwtImage() {
    super( ELEMENT_NAME );
    image = new Image() {
      @Override
      public void onBrowserEvent( Event event ) {

        if ( ( event.getTypeInt() & Event.ONKEYDOWN ) == Event.ONKEYDOWN  ) {
          if ( (char) event.getKeyCode() == KeyCodes.KEY_ENTER ) {
            ElementUtils.click( image.getElement() );
          }
        }
        super.onBrowserEvent( event );
      }
    };
    this.imageUtil = new ImageUtil();
    setManagedObject( image );
    imageUtil.setImageDefaults( image );
    image.setStyleName( "xul-image" ); //$NON-NLS-1$
    addKeyboardListener();
  }

  private void addKeyboardListener() {
    this.addKeyDownHandler( event -> {
      switch ( event.getNativeKeyCode() ) {
        case KeyCodes.KEY_SPACE:
          event.preventDefault();
          break;
        case KeyCodes.KEY_ENTER:
          event.preventDefault();
          ElementUtils.click( GwtImage.this.image.getElement() );
          break;
      }
    } );

    this.addKeyUpHandler( event -> {
      if ( event.getNativeKeyCode() == KeyCodes.KEY_SPACE ) {
        event.preventDefault();
        ElementUtils.click( GwtImage.this.image.getElement() );
      }
    } );
  }

  protected HandlerRegistration addKeyDownHandler( KeyDownHandler handler ) {
    return this.image.addDomHandler( handler, KeyDownEvent.getType() );
  }

  protected HandlerRegistration addKeyUpHandler( KeyUpHandler handler ) {
    return this.image.addDomHandler( handler, KeyUpEvent.getType() );
  }

  public GwtImage( Image image, ImageUtil imageUtil ) {
    super( ELEMENT_NAME );
    this.image = image;
    this.imageUtil = imageUtil;
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {

        case DISABLED:
          setDisabled( "true".equals( value ) );
          break;
        case SRC:
          setSrc( value );
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
    setSrc( srcEle.getAttribute( "src" ) ); //$NON-NLS-1$
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "onclick" ) ) ) {
      setOnclick( srcEle.getAttribute( "onclick" ) ); //$NON-NLS-1$
    }
    setDisabled( "true".equals( srcEle.getAttribute( "disabled" ) ) ); //$NON-NLS-1$//$NON-NLS-2$

    // first checks "pen:imagealttext" then other attributes
    initSetImageAltText( srcEle );
  }

  @Override
  public void layout() {
    image.setTitle( this.getTooltiptext() );
    if ( !StringUtils.isEmpty( this.getTooltiptext() ) ) {
      image.setTitle( this.getTooltiptext() );
    }
  }

  @Bindable
  public String getSrc() {
    return image.getUrl();
  }

  /**
   * Retrieve image's alternative text.
   * @return
   */
  public String getImageAltText() {
    return ( image != null ) ? image.getAltText() : null;
  }

  @Bindable
  public void setSrc( String src ) {
    if ( src == null ) {
      image.setUrl( src );
      return;
    }
    image.setUrl( GWT.getModuleBaseURL() + src );
  }

  @Bindable
  public void setSrc( Object img ) {
    if ( img instanceof String ) {
      setSrc( GWT.getModuleBaseURL() + img );
    }
    if ( img instanceof Image ) {
      setSrc( ( (Image) img ).getUrl() );
    } else {
      throw new UnsupportedOperationException();
    }
  }

  public void refresh() {
    // NOTE Empty on purpose
  }

  @Override
  @Bindable
  public void setTooltiptext( String tooltip ) {
    super.setTooltiptext( tooltip );
    image.setTitle( this.getTooltiptext() );
  }

  @Override
  @Bindable
  public void setVisible( boolean visible ) {
    image.setVisible( visible );
  }

  @Override
  @Bindable
  public boolean isVisible() {
    return image.isVisible();
  }

  public String getOnclick() {
    return onclick;
  }

  public void setOnclick( final String onclick ) {
    this.onclick = onclick;
    image.addClickHandler( new ClickHandler() {
      public void onClick( ClickEvent clickEvent ) {
        invoke( onclick, new Object[] {} );
      }
    } );
    image.getElement().getStyle().setProperty( "cursor", "pointer" );
  }

  /**
   * Set the image's alternative text.
   * @param str
   */
  @Bindable
  public void setImageAltText( String str ) {
    if ( image != null ) {
      image.setAltText( str );
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

}
