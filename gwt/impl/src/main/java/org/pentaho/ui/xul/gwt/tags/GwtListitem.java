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

import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtListitem extends AbstractGwtXulComponent implements XulListitem {

  static final String ELEMENT_NAME = "listitem"; //$NON-NLS-1$
  private static final String VALUE = "value"; //$NON-NLS-1$
  private static final String LABEL = "label"; //$NON-NLS-1$
  private String label = "";
  private Object value;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtListitem();
      }
    } );
  }

  private enum Property {
    ID, LABEL, VALUE
  }

  public GwtListitem() {
    super( ELEMENT_NAME );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    if ( !StringUtils.isEmpty( srcEle.getAttribute( LABEL ) ) ) {
      setLabel( srcEle.getAttribute( LABEL ) );
    }
    if ( !StringUtils.isEmpty( srcEle.getAttribute( VALUE ) ) ) {
      setValue( srcEle.getAttribute( VALUE ) );
    }
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
        case VALUE:
          setValue( value );
          setValue( value );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public String getLabel() {
    return label;
  }

  public void setLabel( String label ) {
    this.label = label;
  }

  public String toString() {
    return getLabel();
  }

  public void layout() {
  }

  public Object getValue() {
    if ( value != null ) {
      return value;
    }
    return getLabel();
  }

  public boolean isSelected() {
    return false;
  }

  public void setSelected( boolean selected ) {
  }

  public void setValue( Object value ) {
    this.value = value;
  }

}
