package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;

public class GwtCheckbox extends AbstractGwtXulComponent implements XulCheckbox {
  
  static final String ELEMENT_NAME = "checkbox"; //$NON-NLS-1$
  private String command;
  private boolean checked;
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtCheckbox();
      }
    });
  }
  
  private CheckBox checkBox;
  
  public GwtCheckbox() {
    super(ELEMENT_NAME);
    managedObject = checkBox = new CheckBox();
    checkBox.setStylePrimaryName("checkbox");
    checkBox.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        try{
          setChecked(checkBox.getValue());
          if(command != null && command.length() > 0) {
            GwtCheckbox.this.getXulDomContainer().invoke(command, new Object[]{});
          }
        } catch(XulException e){
          e.printStackTrace();
        }
      }
    });
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setChecked("true".equals(srcEle.getAttribute("checked")));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
    setCommand(srcEle.getAttribute("command"));
    if(srcEle.getAttribute("pen:class") != null && srcEle.getAttribute("pen:class").length() > 0){
      setClass(srcEle.getAttribute("pen:class"));
    }
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulCheckbox#getSelected()
   */
  @Bindable
  public boolean getSelected() {
    return checkBox.getValue();
  }
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulCheckbox#setSelected(boolean)
   */
  @Bindable
  public void setSelected(boolean selected) {
    checkBox.setValue(selected);
  }
  

  public void layout(){
    checkBox.setTitle(this.getTooltiptext());
  }
  
  @Bindable
  public void setLabel(String label){
    checkBox.setText(label);
  }

  @Bindable
  public String getLabel() {
    return checkBox.getText();
  }

  @Bindable
  public boolean isChecked() {
    return checkBox.getValue();

  }

  @Bindable
  public boolean isDisabled() {
    return !checkBox.isEnabled();
  }

  @Bindable
  public void setChecked(boolean checked) {
   boolean previousVal = this.checked;
   if(checked != checkBox.getValue()) {
     checkBox.setValue(checked); 
   }
   this.checked = checked;
   this.firePropertyChange("checked", previousVal, checked);
  }

  @Bindable
  public void setDisabled(boolean dis) {
    checkBox.setEnabled(!dis);
  }

  public void setCommand(final String command) {
    this.command = command;
  }
  
  public String getCommand(){
    return command;
  }
  
  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("label") != null){
      setLabel(component.getAttributeValue("label"));
    }
    if(component.getAttributeValue("checked") != null){
      setChecked("true".equals(component.getAttributeValue("checked")));
    }
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
  }
  
  public void setClass(String className){
    checkBox.setStylePrimaryName(className);
  }

  @Override
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    checkBox.setTitle(this.getTooltiptext());
  }
  
  
}
