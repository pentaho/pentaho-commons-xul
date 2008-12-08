package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.TextBox;

public class GwtTextbox extends AbstractGwtXulComponent implements XulTextbox {
  
  static final String ELEMENT_NAME = "textbox"; //$NON-NLS-1$

  protected String max, min, type, oninput;
  protected boolean readonly;
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTextbox();
      }
    });
  }
  
  private TextBox textBox;
  
  public GwtTextbox() {
    super(ELEMENT_NAME);
    managedObject = textBox = new TextBox();
    // textBox.setPreferredSize(new Dimension(150,18));
  }

  public void init(com.google.gwt.xml.client.Element srcEle) {
    super.init(srcEle);
    setValue(srcEle.getAttribute("value"));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
  }
  
  public String getValue(){
    return textBox.getText();
  }

  public void setValue(String text){
    textBox.setText(text);
  }
  
  public void layout(){
  }

  public int getMaxlength() {
    return 0;
  }

  public boolean isDisabled() {
    return !textBox.isEnabled();
  }

  public void setDisabled(boolean dis) {
    textBox.setEnabled(!dis);
  }

  public void setMaxlength(int length) {
  }

  public boolean isMultiline() {
    return false;
  }

  public void setMultiline(boolean multi) {
    
  }

  public String getMax() {
    return min;  
  }

  public String getMin() {
    return min;  
  }

  public Object getTextControl() {
   return this.managedObject;   
  }

  public String getType() {
    return type;  
  }

  public boolean isReadonly() {
    return readonly;  
  }

  public void selectAll() {
    
        // TODO Auto-generated method stub 
      
  }

  public void setFocus() {
    // TODO focus method impl
  }

  public void setMax(String max) {
    this.max = max;
  }

  public void setMin(String min) {
    this.min = min;
  }

  public void setOninput(String method) {
    this.oninput = method;
  }

  public void setReadonly(boolean readOnly) {
    this.readonly = readOnly;
  }

  public void setType(String type) {
    this.type = type;
  }
  

  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("value") != null){
      setValue(component.getAttributeValue("value"));
    }
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
  }
}
