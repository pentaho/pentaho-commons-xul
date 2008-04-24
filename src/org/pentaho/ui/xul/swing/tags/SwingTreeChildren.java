package org.pentaho.ui.xul.swing.tags;

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
	public SwingTreeChildren(XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("treechildren");
		
		tree = (XulTree) parent;
		
		managedObject = "empty";
		
	}

	public void addItem(XulTreeItem item) {
		// TODO Auto-generated method stub
		
	}

	public XulTreeRow addNewRow() {

    SwingTreeItem item = new SwingTreeItem(this);
    item.setRow(new SwingTreeRow(item));
    return item.getRow();
	}

	public XulTreeItem getItem(int rowIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public XulTree getTree() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	public void removeItem(int rowIndex) {
		// TODO Auto-generated method stub
		
	}

	public void setAlternatingbackground(boolean alt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void layout() {
		tree.setRootChildren(this);
		
	}
}
