/**
 * 
 */
package org.pentaho.ui.xul.components;

/**
 * The interface for the checkbox XUL widget.
 *
 * @author nbaker
 */
public interface XulCheckbox {
  
  /**
   * Sets/clears the check in the box.
   * 
   * @param checked if true, checks the box, clears it otherwise.
   */
  public void setChecked(boolean checked);
  
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
   * @param label the checkbox label that should display. 
   */
  public void setLabel(String label);
  
  /**
   * XUL's attribute is "disabled", thus this acts
   * exactly the opposite of SWT/Swing/AWT. If the property is not 
   * available, then the control is enabled. 
   *
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled();
  
  /**
   * 
   * @param dis If true, disable this button. Otherwise,
   * attribute should be removed. 
   */
  public void setDisabled(boolean dis);
  
}
