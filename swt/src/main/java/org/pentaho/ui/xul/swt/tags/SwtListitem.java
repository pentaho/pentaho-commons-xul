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
