package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulRadio;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RadioButton;

public class GwtRadio extends AbstractGwtXulComponent implements XulRadio {
  
  static final String ELEMENT_NAME = "radio"; //$NON-NLS-1$
  private String command;
  private boolean checked;
  private String value;
  public static GwtRadioGroup currentGroup;
  public GwtRadioGroup radioGroup;
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtRadio();
      }
    });
  }
  
  private RadioButton radioButton;
  
  public GwtRadio() {
    super(ELEMENT_NAME);
    String id = "foo";
    if(currentGroup != null){
      id = currentGroup.getId();
    }
    radioButton = new RadioButton(id);
    setManagedObject(radioButton);
    radioButton.setStylePrimaryName("radio");
    radioButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        try{
          setChecked(radioButton.getValue());
          if(command != null && command.length() > 0) {
            GwtRadio.this.getXulDomContainer().invoke(command, new Object[]{});
          }
        } catch(XulException e){
          e.printStackTrace();
        }
      }
    });
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    radioGroup = currentGroup;
    if(currentGroup != null){
      currentGroup.registerRadio(this);
    }
    
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setValue(srcEle.getAttribute("value"));
    setChecked("true".equals(srcEle.getAttribute("checked")));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
    setCommand(srcEle.getAttribute("command"));
    if(srcEle.getAttribute("pen:class") != null && srcEle.getAttribute("pen:class").length() > 0){
      setClass(srcEle.getAttribute("pen:class"));
    }
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulRadio#getSelected()
   */
  @Bindable
  public boolean isSelected() {
    return radioButton.getValue();
  }
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulRadio#setSelected(boolean)
   */
  @Bindable
  public void setSelected(boolean selected) {
    radioButton.setValue(selected);
  }
  

  public void layout(){
    radioButton.setTitle(this.getTooltiptext());
  }
  
  @Bindable
  public void setLabel(String label){
    radioButton.setText(label);
  }

  @Bindable
  public String getLabel() {
    return radioButton.getText();
  }

  @Bindable
  public boolean isChecked() {
    return radioButton.getValue();

  }

  @Bindable
  public boolean isDisabled() {
    return !radioButton.isEnabled();
  }

  @Bindable
  public void setChecked(boolean checked) {
   boolean previousVal = this.checked;
   if(checked != radioButton.getValue()) {
     radioButton.setValue(checked); 
   }
   this.checked = checked;
   this.firePropertyChange("checked", previousVal, checked);
   if(this.radioGroup != null){
     this.radioGroup.fireValueChanged();
   }
  }

  @Bindable
  public void setDisabled(boolean dis) {
    radioButton.setEnabled(!dis);
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
    if(component.getAttributeValue("value") != null){
      setValue(component.getAttributeValue("value"));
    }
    if(component.getAttributeValue("checked") != null){
      setChecked("true".equals(component.getAttributeValue("checked")));
    }
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
  }
  
  public void setClass(String className){
    radioButton.setStylePrimaryName(className);
  }

  @Override
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    radioButton.setTitle(this.getTooltiptext());
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String aValue) {
    String previousVal = this.value;
    this.value = aValue;    
    this.firePropertyChange("value", previousVal, aValue);
  }    
}
