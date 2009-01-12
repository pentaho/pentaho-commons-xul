package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtListitem extends AbstractGwtXulComponent implements XulListitem {
  
  static final String ELEMENT_NAME = "listitem"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtListitem();
      }
    });
  }
  
  private String label;
  private Object value;
  
  public GwtListitem() {
    super(ELEMENT_NAME);
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setValue(srcEle.getAttribute("value"));
  }
  public String getLabel(){
    return label;
  }
  
  public void setLabel(String label){
    this.label = label;
    managedObject = this.label;
  }
  
  public String toString(){
    return this.label;
  }
  
  public void layout(){
  }

  public Object getValue() {
    if (value != null) {
      return value;
    }
    return label;
  }

  public boolean isSelected() {
    return false;
  }

  public void setSelected(boolean selected) {
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public void adoptAttributes(XulComponent component) {
    
    if(component.getAttributeValue("label") != null){
      setLabel(component.getAttributeValue("label"));
    }
  }
}
