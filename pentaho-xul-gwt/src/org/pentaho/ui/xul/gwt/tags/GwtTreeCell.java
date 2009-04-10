package org.pentaho.ui.xul.gwt.tags;

import java.util.Vector;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeCell extends AbstractGwtXulComponent implements XulTreeCell {

  private Object value;
  private int selectedIndex;
  
  public static void register() {
    GwtXulParser.registerHandler("treecell",
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeCell();
      }
    });
  }
  
  public GwtTreeCell() {
    super("treecell");
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setValue(srcEle.getAttribute("value"));
  }

  public String getLabel() {
    return getAttributeValue("label");
  }

  public int getSelectedIndex() {
    return selectedIndex;
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getValue() {
    return this.value;
  }

  public boolean isEditable() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setEditable(boolean edit) {
    // TODO Auto-generated method stub
    
  }

  public void setLabel(String label) {
    String prevVal = this.getAttributeValue("label");
    this.setAttribute("label", label);
    this.firePropertyChange("label", prevVal, label);
  }

  public void setSelectedIndex(int index) {
    int oldVal = this.selectedIndex;
    this.selectedIndex = index;
    this.firePropertyChange("selectedIndex", oldVal, index);
    
  }

  public void setSrc(String srcUrl) {
    // TODO Auto-generated method stub
    
  }

  public void setTreeRowParent(XulTreeRow row) {
    // TODO Auto-generated method stub
    
  }

  public void setValue(Object value) {
    Object previousVal = this.value;
    
    if (value instanceof String && ((String) value).indexOf(",") == -1) {
      //String and not a comma separated list
      this.value = Boolean.parseBoolean(((String) value));
    } else if (value instanceof String && ((String) value).indexOf(",") > -1) {
      //String and a comma separated list
      String[] list = ((String) value).split(",");
      Vector<String> vec = new Vector<String>();
      for (String item : list) {
        vec.add(item);
      }
      this.value = vec;

    } else if (value instanceof Boolean) {
      this.value = (Boolean) value;
    } else {
      this.value = value;
    }
    this.firePropertyChange("value", previousVal, value);
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

  private boolean disabled = false;
  @Override
  public boolean isDisabled() {
    return disabled;
  }

  @Override
  public void setDisabled(boolean disabled){
    boolean prevVal = this.disabled;
    this.disabled = disabled;
    super.setDisabled(disabled);
    this.firePropertyChange("disabled", prevVal, disabled);
  }
}
