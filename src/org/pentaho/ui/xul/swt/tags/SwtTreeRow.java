package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.RowWidget;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.custom.TableItemWrapper;
import org.pentaho.ui.xul.swt.custom.TreeItemWrapper;

public class SwtTreeRow extends SwtElement implements XulTreeRow {
  
  protected RowWidget widget;
  protected Item item;
  protected XulTreeItem rowParent;
  
  private List <XulTreeCell> cells = null;
  
  int cellCount = 0;
  
  public SwtTreeRow(XulComponent parent){
    this(parent, null, "treerow");
  }
  
  public SwtTreeRow(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    rowParent = (XulTreeItem)parent;
    
    widget =  (rowParent.isHierarchical()) ? new TreeItemWrapper(rowParent.getTree()) 
                                        : new TableItemWrapper(rowParent.getTree());
    item = widget.getItem();
    managedObject = item;
    
    cells = new ArrayList<XulTreeCell>();
    
    rowParent.setRow(this);
    
  }

  public void addCell(XulTreeCell cell) {
    cells.add(cell);
  }

  @Override
  public void layout() {
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
}
