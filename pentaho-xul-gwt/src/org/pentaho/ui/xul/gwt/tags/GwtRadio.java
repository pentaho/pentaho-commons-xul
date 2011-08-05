package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulRadio;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.user.client.ui.RadioButton;

public class GwtRadio extends AbstractGwtXulComponent implements XulRadio {
  
  static final String ELEMENT_NAME = "radio"; //$NON-NLS-1$
  private String command;
  private boolean checked;
  private String value;
  public static GwtRadioGroup currentGroup;
  public GwtRadioGroup radioGroup;

  private boolean customValue = false;

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtRadio();
      }
    });
  }
  
  private RadioButton radioButton;
  private HorizontalPanel radioContainer;
  private TextBox customValueTextBox;

  public GwtRadio() {
    super(ELEMENT_NAME);

    radioContainer = new HorizontalPanel();
    radioContainer.setSpacing(0);

    customValueTextBox = new TextBox();
    customValueTextBox.setEnabled(false);

    String id = "foo";
    if(currentGroup != null){
      id = currentGroup.getId();
    }
    radioButton = new RadioButton(id);
    radioContainer.add(radioButton);

    setManagedObject(radioContainer);
    radioContainer.setStylePrimaryName("radio");
    radioButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        try{
          fireChangedEvents(radioButton.getValue());
          if (isCustomValue() && isChecked()) {
            customValueTextBox.setEnabled(true);
          }
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
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setValue(srcEle.getAttribute("value"));
    setChecked("true".equals(srcEle.getAttribute("checked")));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
    setCommand(srcEle.getAttribute("command"));
    setCustomValue("true".equals(srcEle.getAttribute("customValue")));

    if (isCustomValue()) {
      customValueTextBox.addKeyUpHandler(new KeyUpHandler() {
        public void onKeyUp(KeyUpEvent event) {
          setValue(customValueTextBox.getValue());
        }
      });
      radioContainer.add(customValueTextBox);
//      customValueTextBox.setWidth("75%");
    }

    if(currentGroup != null){
      currentGroup.registerRadio(this);
    }
    

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
    fireChangedEvents(selected);
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
    
   if(checked != radioButton.getValue()) {
     radioButton.setValue(checked, false);
   }
   fireChangedEvents(checked);
  }

  public void fireChangedEvents(boolean checked){

   boolean previousVal = this.checked;
   this.checked = checked;
   this.firePropertyChange("checked", previousVal, checked);
   this.firePropertyChange("selected", previousVal, checked);
  }

  @Bindable
  public void setDisabled(boolean dis) {
    radioButton.setEnabled(!dis);
    if (isCustomValue()) {
      customValueTextBox.setEnabled(!dis && isSelected());
    }
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
  
  @Bindable
  public String getValue() {
    return value;
  }

  @Bindable
  public void setValue(String aValue) {
    String previousVal = this.value;
    this.value = aValue;
    if (isCustomValue() && isChecked()) {
      customValueTextBox.setEnabled(true);
    }
    this.firePropertyChange("value", previousVal, aValue);
  }

  public boolean isCustomValue() {
    return customValue;
  }

  public void setCustomValue(boolean customValue) {
    this.customValue = customValue;
  }

  protected void disableCustomValueTextBox() {
    if (isCustomValue() && !isSelected()) {
      customValueTextBox.setEnabled(false);
      customValueTextBox.setValue("");
    }
  }

}
