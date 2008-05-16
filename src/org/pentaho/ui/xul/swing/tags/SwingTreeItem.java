package org.pentaho.ui.xul.swing.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;

public class SwingTreeItem extends SwingElement implements XulTreeItem{
	private static final Log logger = LogFactory.getLog(SwingTreeItem.class);
	private XulTreeRow row;
	
	public SwingTreeItem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("treeitem");
		managedObject = "empty";
	}

	public SwingTreeItem(XulTreeRow row) {
		super("treeitem");
		
		try {
      this.element = DocumentFactory.createElement("treeitem", this);
    } catch (XulException e) {
      throw new IllegalArgumentException("error creating treeitem", e);
    }
		
		this.children.add(row);
		super.addChild(row);
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
		super.addChild(row);
	}
	
	@Override
	public void layout() {
		for(XulComponent comp : this.children){ //should be the only one in there
			if(comp instanceof XulTreeRow){ //more of an assert, should be true;
				this.row = (SwingTreeRow) comp;
			} else {
				logger.error("SwingTreeItem's child is not a XulTreeRow");
			}
		}
	}
}
