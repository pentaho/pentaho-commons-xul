package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
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
    // TODO Auto-generated method stub
    return 0;
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getValue() {
    return getAttributeValue("value");
  }

  public boolean isEditable() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setEditable(boolean edit) {
    // TODO Auto-generated method stub
    
  }

  public void setLabel(String label) {
    this.setAttribute("label", label);
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
    this.setAttribute("value", "" + value);
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
