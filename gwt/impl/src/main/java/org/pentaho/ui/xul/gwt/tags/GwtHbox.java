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

import org.pentaho.gwt.widgets.client.panel.HorizontalFlexPanel;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GwtUIConst;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Align;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class GwtHbox extends AbstractGwtXulContainer implements XulHbox {

  static final String ELEMENT_NAME = "hbox"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtHbox();
      }
    } );
  }

  private enum Property {
    PADDING, ID
  }

  Align alignment;

  public GwtHbox() {
    super( ELEMENT_NAME );
    this.orientation = Orient.HORIZONTAL;
    container = createManagedPanel();
    setManagedObject( container );
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

  @Override
  @Bindable
  public void setSpacing( int spacing ) {
    super.setSpacing( spacing );
    ( (HorizontalPanel) container ).setSpacing( spacing );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setAlign( srcEle.getAttribute( "align" ) );
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

  public void layout() {
    super.layout();
  }

  @Override
  public void setBgcolor( String bgcolor ) {
    if ( container != null ) {
      container.getElement().getStyle().setProperty( "backgroundColor", bgcolor );
    }
  }

  public static HorizontalPanel createManagedPanel() {
    return createManagedPanel( GwtUIConst.PANEL_SPACING );
  }

  public static HorizontalPanel createManagedPanel( int defaultSpacing ) {
    return new HorizontalFlexPanel() {
      {
        addStyleName( "hbox" );
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
