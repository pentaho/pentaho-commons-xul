package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulTextbox;

public class SwtTextbox extends XulElement implements XulTextbox {
  private static final long serialVersionUID = 4928464432190672877L;

  private org.eclipse.swt.widgets.Text textBox;
  private boolean disabled = false;
  private int maxLength;

  public SwtTextbox(XulElement parent, String tagName) {
    super(tagName);
    textBox = new org.eclipse.swt.widgets.Text((Composite)parent.getManagedObject(), SWT.NONE);
    managedObject = textBox;
  }

  /**
   * Per XUL documentation, the value attribute "only holds the 
   * default value and is never modified when the user enters 
   * text". So we do not provide a getter, we only provide the 
   * setter method to allow the default to be set, and then do 
   * not hold on to it.   
   * @param text
   */
  public void setValue( String text ) {
    textBox.setText(text);
  }
  
  public String getValue(){
    return textBox.getText();
  }
 
  /**
   * XUL's attribute is "disabled", thus this acts
   * exactly the opposite of SWT. If the property is not 
   * available, then the control is enabled. 
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (!textBox.isDisposed()) textBox.setEnabled( !disabled );
  }
  
  /**
   * @return int The maximum number of characters that the textbox allows to be entered.
   */
  public int getMaxlength(){
    return maxLength;
  }

  public void setMaxlength(int length){
    this.maxLength = length;
    textBox.setTextLimit(maxLength);
  }
  

}
