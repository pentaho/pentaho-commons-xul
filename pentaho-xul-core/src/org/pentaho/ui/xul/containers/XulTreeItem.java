package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulTreeItem extends XulContainer {

  public void setContainer(boolean isContainer);
  public boolean isContainer();
  
  public void setEmpty(boolean empty);
  public boolean isEmpty();
  
  public boolean isHierarchical();
  public XulTree getTree();
  
  public XulTreeRow getRow();
  public void setRow(XulTreeRow row);
  
  public void remove();
  
  public void setImage(String src);
  public String getImage();
  
  public void setExpanded(boolean expanded);
  public boolean isExpanded();
  
  public void setBoundObject(Object obj);
  public Object getBoundObject();

  public void setClassname(String classname);
  public String getClassname();
}
