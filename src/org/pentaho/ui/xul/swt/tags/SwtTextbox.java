package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtTextbox extends SwtElement implements XulTextbox {
  private static final long serialVersionUID = 4928464432190672877L;

  private org.eclipse.swt.widgets.Text textBox;
  private Composite parentComposite = null;
  private boolean disabled = false;
  private boolean multiLine = false;
  private int maxLength;
  private String text;

  public SwtTextbox(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    parentComposite = (Composite)parent.getManagedObject();
    textBox = new org.eclipse.swt.widgets.Text(parentComposite, SWT.BORDER);
    managedObject = textBox;
  }

  /**
   * @param text
   */
  public void setValue( String text ) {
    this.text = text;
    if ((!textBox.isDisposed()) && (text != null)) textBox.setText(text);
  }
  
  public String getValue(){
    if (!textBox.isDisposed()){
      text = textBox.getText();
    }
    return text;
  }
 
  public boolean isDisabled() {
    if (!textBox.isDisposed()){
      disabled = !textBox.isEnabled();
    }
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
    if (!textBox.isDisposed()){
      maxLength = textBox.getTextLimit();
    }
    return maxLength;
  }

  public void setMaxlength(int length){
    this.maxLength = length;
    if ((!textBox.isDisposed()) && (maxLength > 0)) textBox.setTextLimit(maxLength);
  }

  public boolean isMultiline() {
    if (!textBox.isDisposed()){
      multiLine = textBox.getLineCount() > 1;
    }
    return multiLine;
  }

  public void setMultiline(boolean multi) {
    
    if (multi == multiLine){
        return; // nothing has changed...
    }
    multiLine = multi;
    textBox.dispose();
  }

  @Override
  /**
   * Override here because the multiline attribute requires new 
   * construction; We can't ever guarantee the order of the setters, and
   * I think this is the most efficient way to reconstruct the textBox... 
   * May fall down if there are other ways to get to the managed object. 
   */
  public Object getManagedObject() {
    if (textBox.isDisposed()){
      int style = isMultiline()? SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL : SWT.BORDER;
      textBox = new org.eclipse.swt.widgets.Text(parentComposite, style);
      setDisabled(disabled);
      setMaxlength(maxLength);
      setValue(text);
      managedObject = textBox;
    }
    return managedObject;
  }
  

}
