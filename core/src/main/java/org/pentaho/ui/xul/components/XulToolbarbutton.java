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

package org.pentaho.ui.xul.components;

public interface XulToolbarbutton extends XulButton {

  public void setDownimage( String img );

  public String getDownimage();

  public String getDownimagedisabled();

  public void setDownimagedisabled( String img );

  /**
   * Sets the selected state of the button. If this button is a member of a button group, the other buttons will be
   * de-selected.
   * 
   * @param selected
   * @param fireEvent
   *          fire command object on this call
   */
  public void setSelected( boolean selected, boolean fireEvent );
}
