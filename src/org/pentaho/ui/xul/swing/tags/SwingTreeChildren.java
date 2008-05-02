package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swt.tags.SwtTreeItem;
import org.pentaho.ui.xul.swt.tags.SwtTreeRow;

public class SwingTreeChildren extends SwingElement implements XulTreeChildren{
	
	XulTree tree;
	List<XulTreeItem> items;
	public SwingTreeChildren(XulComponent parent, XulDomContainer domContainer, String tagName) {
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
    this.addChild(item);
    
    return item.getRow();
	}

	public XulTreeItem getItem(int rowIndex) {
		return items.get(rowIndex);
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

	public void removeItem(XulTreeItem item) {

		items.remove(item);
	}

	public void removeItem(int rowIndex) {
		items.remove(rowIndex);
	}

	public void setAlternatingbackground(boolean alt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void layout() {
		tree.setRootChildren(this);
	}

	@Override
	public void addComponent(XulComponent c) {
		super.addComponent(c);
		items.add((XulTreeItem) c);
	}
}
