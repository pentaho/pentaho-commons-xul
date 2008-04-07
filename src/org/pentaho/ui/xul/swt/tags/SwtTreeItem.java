package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtTreeItem extends SwtElement implements XulTreeItem {

  protected XulTreeChildren parentTreeChildren = null;
  
  protected XulTreeRow row;
  
  protected XulTreeChildren treeChildren;

  private boolean container = false;
  
  private boolean empty = false;
  
  public SwtTreeItem(XulComponent parent){
    this(parent, null, "treeitem");
  }
  
  public SwtTreeItem(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    parentTreeChildren = (XulTreeChildren)parent;
    parentTreeChildren.addItem(this);
    
  }
  
  public void addTreeChildren(XulTreeChildren children){
    this.treeChildren = children;
  }
  
  public boolean isContainer() {
    return container;
  }

  public boolean isEmpty() {
    return empty;
  }

  public void setContainer(boolean isContainer) {
    container = isContainer;
  }

  public void setEmpty(boolean empty) {
    this.empty = empty;
    
  }

  public boolean isHierarchical() {
    return parentTreeChildren.isHierarchical();
  }
  
  public XulTree getTree(){
    return parentTreeChildren.getTree();
  }

  public XulTreeRow getRow() {
    return row;
  }

  public void setRow(XulTreeRow row) {
    this.row = row;
  }

}
