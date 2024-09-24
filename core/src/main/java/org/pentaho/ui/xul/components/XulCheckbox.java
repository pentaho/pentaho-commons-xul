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
 * The interface for the checkbox XUL widget.
 * 
 * @author nbaker
 */
public interface XulCheckbox extends XulComponent {

  /**
   * Sets/clears the check in the box.
   * 
   * @param checked
   *          if true, checks the box, clears it otherwise.
   */
  public void setChecked( boolean checked );

  /**
   * 
   * @return is the box checked?
   */
  public boolean isChecked();

  /**
   * The label that appears next to the checkbox.
   * 
   * @return the checkbox label.
   */
  public String getLabel();

  /**
   * Sets the label that appears next to the checkbox.
   * 
   * @param label
   *          the checkbox label that should display.
   */
  public void setLabel( String label );

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

  /**
   * According to XUL spec, this is the appropriate event to listen to for checkbox state changes from a user
   * interface. Note that to remain pure to the spec, this will not report a state change set via code or script.
   * 
   * @see CheckboxStateChange at http://www.xulplanet.com/references/elemref/ref_EventHandlers.html
   * 
   * @param method
   *          The method to execute when the checkbox is checked or unchecked.
   */
  public void setCommand( final String method );

  /**
   * @return method The method to execute when the checkbox is checked or unchecked
   */
  public String getCommand();

}
