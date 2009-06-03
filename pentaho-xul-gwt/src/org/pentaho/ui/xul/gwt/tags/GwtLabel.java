package org.pentaho.ui.xul.gwt.tags;


import org.pentaho.gwt.widgets.client.text.ToolTip;
import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.Label;

public class GwtLabel extends AbstractGwtXulComponent implements XulLabel {
  
  static final String ELEMENT_NAME = "label"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtLabel();
      }
    });
  }
  
  private Label label;
  
  public GwtLabel() {
    super(ELEMENT_NAME);
    managedObject = label = new Label();
    label.setStyleName("xul-label");
    label.setWordWrap(true);
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setValue(srcEle.getAttribute("value"));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
  }
  
  public void layout(){
    label.setTitle(this.getTooltiptext());
    if(StringUtils.isEmpty(this.getTooltiptext()) == false){
      label.addMouseListener(new ToolTip(this.getTooltiptext(), 1000));
    }
  }

  public void setValue(String value){
    label.setText(value);
  }
  
  public String getValue(){
    return label.getText();
  }

  public boolean isDisabled() {
    // style here?
    return false;
//    return !label.isEnabled();
  }

  public void setDisabled(boolean dis) {
//    label.setEnabled(!dis);
  }
  

  
  public void adoptAttributes(XulComponent component) {
    
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
    if(component.getAttributeValue("value") != null){
      setValue(component.getAttributeValue("value"));
    }
  }
}
