package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTreeItem extends SwingElement implements XulTreeItem{
	private XulTreeRow row;
	
	public SwingTreeItem(XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("treeitem");
		managedObject = "empty";
	}

	public SwingTreeItem(XulTreeRow row) {
		super("treeitem");
		this.children.add(row);
		this.row = row;
		managedObject = "empty";
	}
	
	public SwingTreeItem(XulTreeChildren parent) {
		super("treeitem");
		managedObject = "empty";
	}

	public XulTreeRow getRow() {
		return row;
	}

	public XulTree getTree() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isContainer() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isHierarchical() {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove() {
		// TODO Auto-generated method stub
		
	}

	public void setContainer(boolean isContainer) {
		// TODO Auto-generated method stub
		
	}

	public void setEmpty(boolean empty) {
		// TODO Auto-generated method stub
		
	}

	public void setRow(XulTreeRow row) {
		this.row = row;
	}
	
	@Override
	public void layout() {
	}
}
