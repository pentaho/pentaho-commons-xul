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
  
  protected List <XulTreeRow> rows;
  
  protected List <XulTreeChildren> treeChildren;

  private boolean container = false;
  
  private boolean empty = false;
  
  public SwtTreeItem(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    parentTreeChildren = (XulTreeChildren)parent;
    rows = new ArrayList <XulTreeRow>();
    treeChildren = new ArrayList <XulTreeChildren>();
    parentTreeChildren.addItem(this);
    
  }
  
  public void addRow(XulTreeRow row){
    rows.add(row);
  }
  
  public void addTreeChildren(XulTreeChildren children){
    treeChildren.add(children);
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

  

}
