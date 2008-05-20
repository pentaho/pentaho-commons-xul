package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Item;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.RowWidget;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.custom.TableItemWrapper;
import org.pentaho.ui.xul.swt.custom.TreeItemWrapper;
import org.pentaho.ui.xul.dom.Element;

public class SwtTreeRow extends SwtElement implements XulTreeRow {
  
  protected RowWidget widget;
  protected Item item;
  protected XulTreeItem rowParent;
  
  private List <XulTreeCell> cells = null;
  
  public SwtTreeRow(XulComponent parent){
    this(null, parent, null, "treerow");
  }
  
  public SwtTreeRow(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    rowParent = (XulTreeItem)parent;
    
    widget =  (rowParent.isHierarchical()) ? new TreeItemWrapper(rowParent.getTree()) 
                                        : new TableItemWrapper(rowParent.getTree(), this);
    item = widget.getItem();
    managedObject = item;
    
    cells = new ArrayList<XulTreeCell>();
    
    rowParent.setRow(this);
    
  }

  public void addCell(XulTreeCell cell) {
    cells.add(cell);
  }

  public void addCellText(int index, String text) {
    
    SwtTreeCell cell  = null;
    if(index < cells.size()){
      cell = (SwtTreeCell)cells.get(index);
      cell.setLabel(text);
    }else{
      cell = new SwtTreeCell(this);
      cell.setLabel(text);
    }
    layout();
  }

  @Override
  public void layout() {
    int cellCount = 0;
    for (XulTreeCell cell : cells) {
      widget.setText(cellCount++, cell.getLabel());
    }
    super.layout();
  }
  
  public void makeCellEditable(int index){
    widget.makeCellEditable(index);
  }
  
  public void remove(){
    widget.remove();
    rowParent.setRow(null);
  }

	public XulTreeCell getCell(int index) {
	  if (cells.size()<=index){
	    return null;
	  }
		return this.cells.get(index);
	}

  public int getSelectedColumnIndex() {
    if (rowParent.getTree() == null){
      return -1;
    }
    return rowParent.getTree().getActiveCellCoordinates()[1];
  }
}
