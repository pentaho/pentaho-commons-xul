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
 * The interface for the XUL label widget.
 * 
 * @author nbaker
 * 
 */
public interface XulLabel extends XulComponent {

  /**
   * Sets the text on the label
   * 
   * @param value
   *          The text that should display on the label.
   */
  public void setValue( String value );

  /**
   * Gets the text on the label
   * 
   * @return value The text that should display on the label.
   */
  public String getValue();

  /**
   * XUL's attribute is "disabled", thus this acts exactly the opposite of SWT/Swing/AWT. If the property is not
   * available, then the control is enabled.
   * 
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled();

  /**
   * 
   * @param dis
   *          If true, disable this button. Otherwise, attribute should be removed.
   */
  public void setDisabled( boolean dis );

}
