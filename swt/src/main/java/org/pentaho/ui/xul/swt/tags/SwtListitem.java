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


package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtListitem extends SwtElement implements XulListitem {

  String label = null;

  Object value = null;

  XulComponent parent;

  boolean isSelected = false;

  public SwtListitem( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    this.parent = parent;
  }

  public void setLabel( String text ) {

    XulComponent p = getParent() != null ? getParent() : parent;
    label = text;

    if ( value == null ) {
      setValue( label );
    }

    ( (SwtListbox) p ).updateLabel( this );
  }

  public String getLabel() {

    return label;

  }

  public Object getValue() {

    return value;

  }

  public boolean isSelected() {

    return isSelected;

  }

  public void setSelected( boolean selected ) {
    isSelected = selected;

    if ( value != null ) {

      ( (XulListbox) parent ).setSelectedItem( value );

    }
  }

  public void setValue( Object value ) {

      this.value = value;

      if ( label == null ) {

        label = value.toString();

      }

    XulComponent p = getParent() != null ? getParent() : parent;
    ( (SwtListbox) p ).addItem( value );
  }

}
