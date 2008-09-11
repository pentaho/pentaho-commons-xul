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
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTreeChildren extends SwingElement implements XulTreeChildren {

  XulTree tree;

  List<XulTreeItem> items;

  public SwingTreeChildren(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("treechildren");

    tree = (XulTree) parent;
    items = new ArrayList<XulTreeItem>();
    managedObject = "empty";

  }

  public void addItem(XulTreeItem item) {
    items.add(item);
    this.addChild(item);
  }

  public XulTreeRow addNewRow() {

    SwingTreeItem item = new SwingTreeItem(this);
    item.setRow(new SwingTreeRow(item));
    items.add(item);
    addChild(item);

    return item.getRow();
  }

  public XulTreeItem getItem(int rowIndex) {
    return (items.size() > rowIndex) ? items.get(rowIndex) : null;
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
    if (items == null) {
      return 0;
    }
    return items.size();
  }

  public void removeAll() {
    while (items.size() > 0) {
      removeItem(0, false);
    }
    tree.update();
  }

  public void removeItem(XulTreeItem item) {
    items.remove(item);
    this.removeChild(item);
    tree.update();
  }

  public void removeItem(int rowIndex, boolean refresh) {
    this.removeChild(items.get(rowIndex));
    items.remove(rowIndex);
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
  public void layout() {
    tree.setRootChildren(this);
    initialized = true;
  }

  @Override
  public void addComponent(XulComponent c) {
    super.addComponent(c);
    items.add((XulTreeItem) c);
    if (initialized) {
      resetContainer();
      layout();
    }

  }
}
