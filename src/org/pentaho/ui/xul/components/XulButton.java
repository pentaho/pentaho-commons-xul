/**
 * 
 */
package org.pentaho.ui.xul.components;

/**
 * The interface for the button XUL widget.
 * 
 * @author nbaker
 */
public interface XulButton {
  
  /**
   * 
   * @param label Sets the label for display on or next to this button. 
   */
  public void setLabel(String label);
  
  /**
   * return the label that displays on/next to this button
   * @return the button label
   */
  public String getLabel();
 
  /**
   * TODO: XUL spec recommends using the oncommand attribute
   * to respond to button clicks. We may want to rename or duplicate this 
   * functionality in lieu of the oncommand method. 
   * 
   * Sets the method that will be invoked when this button
   * is pressed. Also hooks up any listeners for this event. 
   * @param method the method to execute when the button is pressed. 
   */
  public void setOnclick(String method);
  
  /**
   * 
   * @param dis If true, disable this button. Otherwise,
   * attribute should be removed. 
   */
  public void setDisabled(boolean dis);
  
  /**
   * XUL's attribute is "disabled", thus this acts
   * exactly the opposite of SWT/Swing/AWT. If the property is not 
   * available, then the control is enabled. 
   *
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled();
  
}
