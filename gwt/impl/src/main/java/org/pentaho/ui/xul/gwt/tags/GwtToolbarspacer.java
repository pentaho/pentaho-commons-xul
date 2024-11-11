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

import com.google.gwt.user.client.ui.SimplePanel;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulToolbarspacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtToolbarspacer extends AbstractGwtXulContainer implements XulToolbarspacer {

  private SimplePanel panel = new SimplePanel();

  private enum Property {
    VISIBLE
  }

  public GwtToolbarspacer() {
    super( "toolbarspacer" );
    setManagedObject( panel );
    // Do not change. GWT widgets Toolbar keys off this to know that the panel is suposed to act as a spacer
    panel.setStyleName( "spacer" );
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {
        case VISIBLE:
          setVisible( "true".equals( value ) );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    String vis = srcEle.getAttribute( "pen:visible" );
    if ( vis != null && !vis.equals( "" ) ) {
      setVisible( "true".equals( vis ) );
    }
  }

  public static void register() {
    GwtXulParser.registerHandler( "toolbarspacer", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbarspacer();
      }
    } );
  }

  @Override
  public void setFlex( int flex ) {
    super.setFlex( flex );
    panel.getElement().setAttribute( "flex", "" + flex );
  }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );
    panel.setVisible( visible );
    // need to collapse space if not visible (style="visibility:hidden" preserves space)
    if ( visible ) {
      panel.setWidth( this.getWidth() + "px" );
    } else {
      panel.setWidth( "0px" );
    }
  }

  @Override
  public void setWidth( int width ) {
    super.setWidth( width );
    if ( this.isVisible() ) {
      panel.setWidth( width + "px" );

      // Used by responsive mode.
      ElementUtils.setStyleProperty( panel.getElement(), "--flex-max-width", width + "px" );
    }
  }
}
