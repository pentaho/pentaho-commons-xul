package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtListitem extends AbstractGwtXulComponent implements XulListitem {
  
  static final String ELEMENT_NAME = "listitem"; //$NON-NLS-1$
  private static final String VALUE = "value"; //$NON-NLS-1$
  private static final String LABEL = "label"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtListitem();
      }
    });
  }
  
  public GwtListitem() {
    super(ELEMENT_NAME);
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    if (!StringUtils.isEmpty(srcEle.getAttribute(LABEL))) {
      setLabel(srcEle.getAttribute(LABEL));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute(VALUE))) {
      setValue(srcEle.getAttribute(VALUE));
    }
  }

  public String getLabel(){
    return getAttributeValue(LABEL);
  }
  
  public void setLabel(String label) {
    setAttribute(LABEL, label);
  }
  
  public String toString(){
    return getLabel();
  }
  
  public void layout(){
  }

  public Object getValue() {
    if (getAttributeValue(VALUE) != null) {
      return getAttributeValue(VALUE);
    }
    return getLabel();
  }

  public boolean isSelected() {
    return false;
  }

  public void setSelected(boolean selected) {
  }

  public void setValue(Object value) {
    setAttribute(VALUE, (String)value);
  }

  public void adoptAttributes(XulComponent component) {
    
    if(component.getAttributeValue(LABEL) != null){
      setLabel(component.getAttributeValue(LABEL));
    }
    
    if(component.getAttributeValue(VALUE) != null){
      setValue(component.getAttributeValue(VALUE));
    }
  }
}
