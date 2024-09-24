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
