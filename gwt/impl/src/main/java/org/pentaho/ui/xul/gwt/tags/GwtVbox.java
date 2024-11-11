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

import com.google.gwt.user.client.ui.VerticalPanel;
import org.pentaho.gwt.widgets.client.panel.VerticalFlexPanel;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GwtUIConst;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Orient;

public class GwtVbox extends AbstractGwtXulContainer implements XulVbox {

  static final String ELEMENT_NAME = "vbox"; //$NON-NLS-1$

  private enum Property {
    PADDING
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtVbox();
      }
    } );
  }

  public GwtVbox() {
    this( ELEMENT_NAME );
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {
        case PADDING:
          setPadding( Integer.valueOf( value ) );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public GwtVbox( String elementName ) {
    super( elementName );
    this.orientation = Orient.VERTICAL;
    container = createManagedPanel();
    setManagedObject( container );
  }

  @Override
  @Bindable
  public void setSpacing( int spacing ) {
    super.setSpacing( spacing );
    ( (VerticalPanel) container ).setSpacing( spacing );
  }

  @Override
  @Bindable
  public void setHeight( int height ) {
    super.setHeight( height );
    container.setHeight( height + "px" );
  }

  @Override
  @Bindable
  public void setWidth( int width ) {
    super.setWidth( width );
    container.setWidth( width + "px" );
  }

  @Override
  public void setBgcolor( String bgcolor ) {
    if ( container != null ) {
      container.getElement().getStyle().setProperty( "backgroundColor", bgcolor );
    }
  }

  public static VerticalPanel createManagedPanel() {
    return createManagedPanel( GwtUIConst.PANEL_SPACING );
  }

  public static VerticalPanel createManagedPanel( int defaultSpacing ) {
    return new VerticalFlexPanel() {
      {
        addStyleName( "vbox" );
        super.setSpacing( defaultSpacing );
      }

      @Override
      public void setSpacing( int spacing ) {
        // IE_6_FIX, move to CSS
        super.setSpacing( spacing );

        // For responsive mode, convert the cell spacing into the flex layout gap.
        ElementUtils.setStyleProperty( getElement(), "--layout-gap-h-local", spacing + "px" );
        ElementUtils.setStyleProperty( getElement(), "--layout-gap-v-local", spacing + "px" );
      }
    };
  }
}
