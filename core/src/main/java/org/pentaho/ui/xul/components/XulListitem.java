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
