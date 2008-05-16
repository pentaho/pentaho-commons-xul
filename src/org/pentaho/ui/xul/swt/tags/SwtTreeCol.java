package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.swt.ColumnWidget;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.custom.TableColumnWrapper;
import org.pentaho.ui.xul.swt.custom.TreeColumnWrapper;
import org.pentaho.ui.xul.util.ColumnType;
import org.pentaho.ui.xul.dom.Element;

public class SwtTreeCol extends SwtElement implements XulTreeCol {


  protected ColumnWidget widget;
  protected Item item;
  protected XulTreeCols columnParent;
  
  private ColumnType type = ColumnType.TEXT;
  private boolean editable = false;
  private String customClass = null;

  public SwtTreeCol(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    columnParent = (XulTreeCols)parent;
    
    widget =  (columnParent.isHierarchical()) ? new TreeColumnWrapper((Tree)columnParent.getTree().getManagedObject()) 
                                        : new TableColumnWrapper((Table)columnParent.getTree().getManagedObject());
    item = widget.getItem();
    managedObject = item;
    columnParent.addColumn(this);
  }
  
  public String getSortDirection() {
    return null;
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getType() {
    return type.toString();
  }
  
  public ColumnType getColumnType(){
    return type;
  }

  public int getWidth() {
    return widget.getWidth();
  }

  public boolean isEditable() {
    return editable;
  }

  public boolean isFixed() {
    return widget.getResizable();
  }

  public boolean isHidden() {
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

  public void setEditable(boolean edit) {
    editable = edit;
  }

  public void setFixed(boolean fixed) {
    widget.setResizable(!fixed);
  }

  public void setHidden(boolean hide) {
    // TODO Auto-generated method stub

  }

  public void setLabel(String label) {
    widget.setText(label);

  }

  public String getLabel() {
    return widget.getText();
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
      this.type = ColumnType.valueOf(type.toUpperCase());
  }

  public void setWidth(int width) {
    widget.setWidth(width);
  }
  
  public void autoSize(){
    widget.autoSize();
  }

  public String getCustomeditor() {
    return customClass;
  }

  public void setCustomeditor(String customClass) {
    this.customClass = customClass;
  }

}
