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
 * The interface for the button XUL widget.
 * 
 * @author nbaker
 */
public interface XulButton extends XulComponent {

  public enum Type {
    RADIO, CHECKBOX
  };

  /**
   * 
   * @param label
   *          Sets the label for display on or next to this button.
   */
  public void setLabel( String label );

  /**
   * return the label that displays on/next to this button
   * 
   * @return the button label
   */
  public String getLabel();

  /**
   * 
   * @param location
   *          of an image to be rendered on the button relative to the xul document.
   */
  public void setImage( String src );

  /**
   * return the location of the image to be dispalyed on the button relative to the xul document
   * 
   * @return the button label
   */
  public String getImage();

  /**
   * TODO: XUL spec recommends using the oncommand attribute to respond to button clicks. We may want to rename or
   * duplicate this functionality in lieu of the oncommand method.
   * 
   * Sets the method that will be invoked when this button is pressed. Also hooks up any listeners for this event.
   * 
   * @param method
   *          the method to execute when the button is pressed.
   */
  public void setOnclick( String method );

  /**
   * TODO: XUL spec recommends using the oncommand attribute to respond to button clicks. We may want to rename or
   * duplicate this functionality in lieu of the oncommand method.
   * 
   * Gets the method that will be invoked when this button is pressed.
   * 
   * @return String method to execute when the button is pressed.
   */
  public String getOnclick();

  /**
   * 
   * @param dis
   *          If true, disable this button. Otherwise, attribute should be removed.
   */
  public void setDisabled( boolean dis );

  /**
   * XUL's attribute is "disabled", thus this acts exactly the opposite of SWT/Swing/AWT. If the property is not
   * available, then the control is enabled.
   * 
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled();

  /**
   * Sets the order of image to label representation.
   * 
   * @param dir
   *          forward or reverse ordering
   */
  public void setDir( String dir );

  /**
   * Get the ordering of elements (forward / reverse)
   * 
   * @return String forward or reverse ordering
   */
  public String getDir();

  /**
   * Set special behaviors for this button (checkbox or radio)
   * 
   * @param type
   *          (checkbox or radio)
   */
  public void setType( String type );

  /**
   * Get the behavior type of the button (checkbox or radio) * @return String the type of button behavior
   */
  public String getType();

  /**
   * Buttons with type="radio" and the same value for their group attribute are put into the same group. Only one
   * button from each group can be checked at a time. If the user selects one the buttons, the others in the group
   * are unchecked.
   * 
   * @param group
   */
  public void setGroup( String group );

  /**
   * Get the radio group name this button belongs to
   * 
   * @param group
   */
  public String getGroup();

  /**
   * Sets the selected state of the button. If this button is a member of a button group, the other buttons will be
   * de-selected.
   * 
   * @param selected
   */
  public void setSelected( String selected );

  /**
   * Sets the selected state of the button. If this button is a member of a button group, the other buttons will be
   * de-selected.
   * 
   * @param selected
   */
  public void setSelected( boolean selected );

  /**
   * Returns the selected state of the button.
   * 
   * @return
   */
  public boolean isSelected();

  /**
   * Fire onclick event
   */
  public void doClick();
}
