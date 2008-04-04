package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulTreeChildren extends XulContainer {
  
  public void setAlternatingbackground(boolean alt);
  public boolean isAlternatingbackground();
  
  public void addItem(XulTreeItem column);
  
  public boolean isHierarchical();
  public XulTree getTree();


}
