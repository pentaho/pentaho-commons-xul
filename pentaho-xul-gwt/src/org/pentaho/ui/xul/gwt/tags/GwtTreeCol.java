package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeCol extends AbstractGwtXulComponent implements XulTreeCol {

  public static void register() {
    GwtXulParser.registerHandler("treecol", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeCol();
      }
    });
  }
  
  String label;

  public GwtTreeCol() {
    super("treecol");
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle) {
    super.init(srcEle);
    setLabel(srcEle.getAttribute("label"));
  }
  
  public void autoSize() {
    // TODO Auto-generated method stub
    
  }

  public String getBinding() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getCustomeditor() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getLabel() {
    return label;
  }

  public String getSortDirection() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getType() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isEditable() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isFixed() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHidden() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isPrimary() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isSortActive() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setBinding(String binding) {
    // TODO Auto-generated method stub
    
  }

  public void setCustomeditor(String customClass) {
    // TODO Auto-generated method stub
    
  }

  public void setEditable(boolean edit) {
    // TODO Auto-generated method stub
    
  }

  public void setFixed(boolean fixed) {
    // TODO Auto-generated method stub
    
  }

  public void setHidden(boolean hide) {
    // TODO Auto-generated method stub
    
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setPrimary(boolean primo) {
    // TODO Auto-generated method stub
    
  }

  public void setSortActive(boolean sort) {
    // TODO Auto-generated method stub
    
  }

  public void setSortDirection(String dir) {
    // TODO Auto-generated method stub
    
  }

  public void setSrc(String srcUrl) {
    // TODO Auto-generated method stub
    
  }

  public void setType(String type) {
    // TODO Auto-generated method stub
    
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
