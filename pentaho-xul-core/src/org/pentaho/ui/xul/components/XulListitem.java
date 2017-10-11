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

/**
 * 
 */

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * The interface for the listitem widget. A listitem is one entry in a listbox.
 * 
 * @author nbaker
 * 
 */
public interface XulListitem extends XulComponent {

  /**
   * The text that is displayed in the box for this listItem. If the label is not set, an attempt is made to set
   * the text by calling toString() on the value object, if present. Otherwise no text is displayed.
   * 
   * @param label
   *          The listitem label to display.
   */
  public void setLabel( String label );

  /**
   * The text that represents this listitem in the box.
   * 
   * @return The listitem label.
   */
  public String getLabel();

  /**
   * Determines whether this item is selected in the box.
   * 
   * @param selected
   *          If true, this item is selected, otherwise, it is not.
   */
  public void setSelected( boolean selected );

  /**
   * 
   * @return Is this listitem selected in the box?
   */
  public boolean isSelected();

  /**
   * A value associated with the listitem. Use for your own purposes. If no label is set on this listitem, an
   * attempt is made to derive a label by calling toString() on this object.
   * 
   * @param value
   *          The object to associate with this listitem.
   */
  public void setValue( Object value );

  /**
   * Likely will be and can be null.
   * 
   * @return The object associated with this listitem.
   */
  public Object getValue();

}
