/**
 * 
 */
package org.pentaho.ui.xul.components;

/**
 * The interface for a Xul textbox widget. 
 * 
 * @author nbaker
 *
 */
public interface XulTextbox {

  /**
   * Sets the value that will display as default in the textbox. 
   * @param str The textbox's default value. 
   */
  public void setValue(String str);
  
  /**
   * 
   * @return The value entered into the textbox. 
   */
  public String getValue();
  
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

  /**
   * 
   * @return int The maximum number of characters that the textbox allows to be entered.
   */
  public int getMaxlength();

  /**
   * Sets the maximum length of the string that can be entered into the textbox.
   *  
   * @param length The number of characters allowed. 
   */
  public void setMaxlength(int length);
  
  /**
   * If true, the textbox displays multiple lines. The text entered in the 
   * textbox will wrap, and scrollbars are available. 
   *  
   * @return If true, is multiline; otherwise is a single line textbox. 
   */
  public boolean isMultiline();
  
  /** 
   * Sets whether the textbox will be multiline or single; Single is
   * the default. 
   * @param multi If true, set to multiple line. 
   */
  public void setMultiline(boolean multi);
  
  
  
  

}
