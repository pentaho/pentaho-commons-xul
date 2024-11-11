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
