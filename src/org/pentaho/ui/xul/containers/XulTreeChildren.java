package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulTreeChildren extends XulContainer {
  
  public void setAlternatingbackground(boolean alt);
  public boolean isAlternatingbackground();
  
  public void addItem(XulTreeItem item);
  public void removeItem(XulTreeItem item);
  
  public XulTreeRow addNewRow();
  
  public void removeAll();
  public XulTreeItem getItem(int rowIndex);
  public void removeItem(int rowIndex);
  public int getItemCount();

  public boolean isHierarchical();
  public XulTree getTree();


}
