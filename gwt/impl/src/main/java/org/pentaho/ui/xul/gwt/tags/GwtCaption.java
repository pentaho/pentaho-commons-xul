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

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtCaption extends AbstractGwtXulComponent implements XulCaption {

  static final String ELEMENT_NAME = "caption"; //$NON-NLS-1$

  private enum Property {
    LABEL
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtCaption();
      }
    } );
  }

  private String caption;

  public GwtCaption() {
    super( ELEMENT_NAME );
    setManagedObject( caption );
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
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    if ( srcEle.hasAttribute( "label" ) ) {
      setLabel( srcEle.getAttribute( "label" ) );
    }
  }

  public String getLabel() {
    return caption;
  }

  public void setLabel( String caption ) {
    this.caption = caption;
    setManagedObject( this.caption );
  }

  public void layout() {
  }

}
