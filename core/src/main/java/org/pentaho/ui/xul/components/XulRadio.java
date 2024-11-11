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


/**
 * 
 */

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * The interface for the radio XUL widget.
 * 
 * @author aphillips
 */
public interface XulRadio extends XulComponent {

  /**
   * Sets/clears the check in the box.
   * 
   * @param checked
   *          if true, checks the box, clears it otherwise.
   */
  public void setSelected( boolean checked );

  /**
   * 
   * @return is the box checked?
   */
  public boolean isSelected();

  /**
   * The label that appears next to the radio.
   * 
   * @return the radio label.
   */
  public String getLabel();

  /**
   * Sets the label that appears next to the radio.
   * 
   * @param label
   *          the radio label that should display.
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
   * According to XUL spec, this is the appropriate event to listen to for radio state changes from a user
   * interface. Note that to remain pure to the spec, this will not report a state change set via code or script.
   * 
   * @see radioStateChange at http://www.xulplanet.com/references/elemref/ref_EventHandlers.html
   * 
   * @param method
   *          The method to execute when the radio is checked or unchecked.
   */
  public void setCommand( final String method );

  /**
   * The value of the radio button.
   * 
   * @return the radio value.
   */
  public String getValue();

  /**
   * Sets the value of the radio button.
   */
  public void setValue( String aValue );
}
