package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.TextType;
import org.pentaho.ui.xul.dom.Element;

public class SwtTextbox extends SwtElement implements XulTextbox {

  private static final long serialVersionUID = 4928464432190672877L;

  protected org.eclipse.swt.widgets.Text textBox;
  protected Composite parentComposite = null;
  private boolean disabled = false;
  private boolean multiLine = false;
  private boolean readOnly = false;
  private int maxLength;
  private String text;
  private TextType type = TextType.NORMAL;
  private int max;
  private int min;

  public SwtTextbox(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    parentComposite = (Composite)parent.getManagedObject();
    textBox = createNewText();
    managedObject = textBox;
  }
  
  public Text createNewText(){
    return new org.eclipse.swt.widgets.Text(parentComposite, SWT.BORDER);
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
      setReadonly(readOnly);
      setType(type);
      managedObject = textBox;
    }
    return managedObject;
  }

  public boolean isReadonly() {
    if (!textBox.isDisposed()){
      readOnly = !textBox.getEditable();
    }
    return readOnly;
  }

  public void setReadonly(boolean readOnly) {
    this.readOnly = readOnly;
    if (!textBox.isDisposed()) textBox.setEditable(!readOnly);
  }

  public String getType() {
    if (type == null){
      return null;
    }
    
    return type.toString();
  }

  public void setType(String type) {
    if (type == null){
      return;
    }
    setType(TextType.valueOf(type.toUpperCase()));
  }
  
  public void setType(TextType type) {
    this.type = type;
    if (this.type == null){
      return;
    }
    if (!textBox.isDisposed()) {
      
      switch(this.type){
        case PASSWORD:
          textBox.setEchoChar('*');
          break;
        default:
          // TODO log not implemented yet for autocomplete, number, timed
      }
    }
    
  }

  public void selectAll() {
    textBox.selectAll();
  }

  public void setFocus() {
    textBox.setFocus();
  }
  
  public Object getTextControl() {
    return getManagedObject();
  }

  public void setOninput(String method) {
    //throw new NotImplementedException();
  }
  public String getMin() {
    return ""+min;
  }

  public void setMin(String min) {
    this.min = Integer.parseInt(min);  
  }

  public String getMax() {
    return ""+max;
  }

  public void setMax(String max) {
    this.max = Integer.parseInt(max);  
  }
}
