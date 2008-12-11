package org.pentaho.ui.xul.gwt.tags;

import java.beans.PropertyChangeListener;
import java.util.Collection;

import org.pentaho.gwt.widgets.client.table.BaseTable;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTree extends AbstractGwtXulContainer implements XulTree {

  public static void register() {
    GwtXulParser.registerHandler("tree", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTree();
      }
    });
  }

  XulTreeCols columns = null;
  XulTreeChildren rootChildren = null;
  
  public GwtTree() {
    super("tree");
  }
  
  public void addChild(Element element) {
    super.addChild(element);
    if (element.getName().equals("treecols")) {
      columns = (XulTreeCols)element;
    } else if (element.getName().equals("treechildren")) {
      rootChildren = (XulTreeChildren)element;
    }
  }
  
  public void addTreeRow(XulTreeRow row) {
      GwtTreeItem item = new GwtTreeItem();
      item.setRow(row);
      this.rootChildren.addItem(item);
      
      // update UI
      populate();
  }
  
  // need to handle layouting
  public void layout() {
    
    String cols[] = new String[getColumns().getColumnCount()];
    int len[] = new int[cols.length];
    // for each column
    for (int i = 0; i < cols.length; i++) {
      cols[i] = getColumns().getColumn(i).getLabel();
      len[i] = 100;
    }
    
    // use base table from pentaho widgets library for now
    
    BaseTable table = new BaseTable(cols, len);
    managedObject = table;
    
    populate();
  }
  
  private void populate() {
    BaseTable table = (BaseTable)managedObject;
    Object data[][] = new Object[getRootChildren().getItemCount()][getColumns().getColumnCount()];
    
    for (int i = 0; i < getRootChildren().getItemCount(); i++) {
      for (int j = 0; j < getColumns().getColumnCount(); j++) {
        data[i][j] = getRootChildren().getItem(i).getRow().getCell(j).getLabel();
      }
    }
    table.populateTable(data);
  }

  public void clearSelection() {
    // TODO Auto-generated method stub
    
  }

  public int[] getActiveCellCoordinates() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulTreeCols getColumns() {
    return columns;
  }

  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public <T> Collection<T> getElements() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOnedit() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOnselect() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulTreeChildren getRootChildren() {
    return rootChildren;
  }

  public int getRows() {
    return rootChildren.getItemCount();
  }

  public int[] getSelectedRows() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getSeltype() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object[][] getValues() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isEditable() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isEnableColumnDrag() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void removeTreeRows(int[] rows) {
    // TODO Auto-generated method stub
    
  }

  public void setActiveCellCoordinates(int row, int column) {
    // TODO Auto-generated method stub
    
  }

  public void setColumns(XulTreeCols columns) {
    if (getColumns() != null) {
      this.removeChild(getColumns());
    }
    addChild(columns);
  }

  public void setData(Object data) {
    // TODO Auto-generated method stub
    
  }

  public void setEditable(boolean edit) {
    // TODO Auto-generated method stub
    
  }

  public <T> void setElements(Collection<T> elements) {
    // TODO Auto-generated method stub
    
  }

  public void setEnableColumnDrag(boolean drag) {
    // TODO Auto-generated method stub
    
  }

  public void setOnedit(String onedit) {
    // TODO Auto-generated method stub
    
  }

  public void setOnselect(String select) {
    // TODO Auto-generated method stub
    
  }

  public void setRootChildren(XulTreeChildren rootChildren) {
    if (getRootChildren() != null) {
      this.removeChild(getRootChildren());
    }
    addChild(rootChildren);
  }

  public void setRows(int rows) {
    // TODO Auto-generated method stub
    
  }

  public void setSelectedRows(int[] rows) {
    // TODO Auto-generated method stub
    
  }

  public void setSeltype(String type) {
    // TODO Auto-generated method stub
    
  }

  public void update() {
    // TODO Auto-generated method stub
    
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }


}
