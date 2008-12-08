package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeCell extends AbstractGwtXulComponent implements XulTreeCell {

  public static void register() {
    GwtXulParser.registerHandler("treecell",
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeCell();
      }
    });
  }
  
  String label;
  Object value;
  
  public GwtTreeCell() {
    super("treecell");
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle) {
    super.init(srcEle);
    setLabel(srcEle.getAttribute("label"));
    setValue(srcEle.getAttribute("value"));
  }

  public String getLabel() {
    return label;
  }

  public int getSelectedIndex() {
    // TODO Auto-generated method stub
    return 0;
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getValue() {
    return value;
  }

  public boolean isEditable() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setEditable(boolean edit) {
    // TODO Auto-generated method stub
    
  }

  public void setLabel(String label) {
    this.label = label;
    
  }

  public void setSelectedIndex(int index) {
    // TODO Auto-generated method stub
    
  }

  public void setSrc(String srcUrl) {
    // TODO Auto-generated method stub
    
  }

  public void setTreeRowParent(XulTreeRow row) {
    // TODO Auto-generated method stub
    
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
