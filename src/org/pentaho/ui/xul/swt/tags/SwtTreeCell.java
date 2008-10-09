package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtTreeCell extends SwtElement implements XulTreeCell {

  String label="";
  String srcUrl=null;
  Object value=null;
  SwtTreeRow parentRow;
  boolean isEditable=false;
  private int selectedIndex = 0;
  
  public SwtTreeCell(XulComponent parent){
    this(null, parent,null,"treecell");
  }

  public SwtTreeCell(Element self, XulComponent parent, XulDomContainer container, String tagName){
    super(tagName);
    if(parent instanceof SwtTreeRow){
      parentRow = (SwtTreeRow) parent;
      parentRow.addCell(this);
      parentRow.layout();
    }
  }
  
  public void setTreeRowParent(XulTreeRow row){
    parentRow = (SwtTreeRow) row;
    parentRow.addCell(this);
  }
  
  public String getLabel() {
    return label;
  }

  public String getSrc() {
    return srcUrl;
  }

  public Object getValue() {
    return value;
  }

  public boolean isEditable() {
    return isEditable;
  }

  public void setEditable(boolean edit) {
    isEditable=edit;
  }

  public void setLabel(String label) {
    String oldVal = this.label;
    this.label=label;
    if(parentRow != null && oldVal != null && !oldVal.equals(label)){
      parentRow.layout();
    }
    this.changeSupport.firePropertyChange("label", oldVal, label);
  }

  public void setSrc(String srcUrl) {
    this.srcUrl=srcUrl;
  }

  public void setValue(Object value) {
    Object oldVal = this.value;
    this.value=value;
    
    if(parentRow != null && oldVal != null && !oldVal.equals(value) && oldVal != value){
      parentRow.layout();
    }
    this.changeSupport.firePropertyChange("value", oldVal, value);
  }

  public int getSelectedIndex() {
    return selectedIndex;
  }

  public void setSelectedIndex(int index) {
    Object oldValue = this.selectedIndex;
    this.selectedIndex = index;
    this.changeSupport.firePropertyChange("selectedIndex", oldValue, index);
  }

}
