package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtTreeItem extends SwtElement implements XulTreeItem {

  protected XulTreeChildren parentTreeChildren = null;
  
  protected XulTreeRow row;
  
  protected XulTreeChildren treeChildren;

  private boolean container = false;
  
  private boolean empty = false;
  
  public SwtTreeItem(XulComponent parent){
    this(null, parent, null, "treeitem");
  }
  
  public SwtTreeItem(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    parentTreeChildren = (XulTreeChildren)parent;
    parentTreeChildren.addItem(this);
    
  }
  
  public void addTreeChildren(XulTreeChildren ch){
    this.treeChildren = ch;
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

  public void remove() {
    row.remove();
    //parentTreeChildren.removeItem(this);
  }

}
