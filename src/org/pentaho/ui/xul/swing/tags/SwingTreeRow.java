package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTreeRow extends SwingElement implements XulTreeRow{
	XulTreeItem treeItem;
	
	public SwingTreeRow(XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("treerow");
		managedObject = "empty";
	}
	
	public SwingTreeRow(XulComponent parent) {
		super("treerow");
		managedObject = "empty";
	}
	

	public void addCell(XulTreeCell cell) {
		this.children.add(cell);
		super.addChild(cell);
	}


  public void addCellText(int index, String text) {
    
    SwingTreeCell cell  = null;
    if(index < children.size()){
      cell = (SwingTreeCell)children.get(index);
      cell.setLabel(text);
    }else{
      cell = new SwingTreeCell(this);
      cell.setLabel(text);
      this.addCell(cell);
    }
    layout();
    ((SwingTree)this.getParent().getParent().getParent()).getTable().updateUI();
  }

	public void makeCellEditable(int index) {
		// TODO Auto-generated method stub
		
	}

	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void layout() {
	}

	public XulTreeCell getCell(int index) {
		return (SwingTreeCell) this.children.get(index);
	}

}
