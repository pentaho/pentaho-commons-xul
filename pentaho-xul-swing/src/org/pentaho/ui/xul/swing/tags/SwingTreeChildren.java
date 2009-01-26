package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTreeChildren extends AbstractSwingContainer implements XulTreeChildren {

  XulTree tree;


  public SwingTreeChildren(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("treechildren");
    
    managedObject = "empty";

  }

  public void addItem(XulTreeItem item) {
    this.addChild(item);
  }

  public XulTreeRow addNewRow() {

    SwingTreeItem item = new SwingTreeItem(this);
    item.setRow(new SwingTreeRow(item));
    addChild(item);

    return item.getRow();
  }

  public XulTreeItem getItem(int rowIndex) {
    return (getChildNodes().size() > rowIndex) ? (XulTreeItem) getChildNodes().get(rowIndex) : null;
  }

  public XulTree getTree() {
    return tree;
  }

  public boolean isAlternatingbackground() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public int getItemCount() {
    return this.getChildNodes().size();
  }

  public void removeAll() {
    while (getChildNodes().size() > 0) {
      removeItem(0, false);
    }
    tree.update();
  }

  public void removeItem(XulTreeItem item) {
    this.removeChild(item);
    tree.update();
  }

  public void removeItem(int rowIndex, boolean refresh) {
    this.removeChild(getChildNodes().get(rowIndex));
    if (refresh) {
    	tree.update();
    }
    
  }
  
  public void removeItem(int rowIndex) {
  	removeItem(rowIndex, true);
  }

  public void setAlternatingbackground(boolean alt) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onDomReady() {
    //TODO: move this into constructor once the "self" element is real
    //Due to many nested TreeItems with their own TreeChildren, we have to walk up the DOm
    //an unknown positions to find the tree.
    if(tree == null){
      XulComponent c = getParent();
    
      while(c != null){
        if(c instanceof XulTree){
          tree = (XulTree) c;
          break;
        }
        System.out.println(c.getName());
        
        c = c.getParent();
      }
    }
    layout();
  }

  @Override
  public void layout() {
    if(tree != null){
      tree.setRootChildren(this);
      initialized = true;
    }
  }

}
