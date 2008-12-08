package org.pentaho.ui.xul.gwt.tags;

import java.util.Collection;

import org.pentaho.gwt.widgets.client.table.BaseTable;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTree extends AbstractGwtXulComponent implements XulTree {

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
  
  public void addTreeRow(XulTreeRow row) {
    // TODO Auto-generated method stub
    
  }
  
  // need to handle layouting
  public void layout() {
    
    // setup columns
    columns = (XulTreeCols)getElementsByTagName("treecols").get(0);
    rootChildren = (XulTreeChildren)getElementsByTagName("treechildren").get(0);
    
    String cols[] = new String[getColumns().getColumnCount()];
    int len[] = new int[cols.length];
    // for each column
    for (int i = 0; i < cols.length; i++) {
      cols[i] = getColumns().getColumn(i).getLabel();
      len[i] = 100;
    }
    
    // use base table from pentaho widgets library for now
    
    BaseTable table = new BaseTable(cols, len);
    
    Object data[][] = new Object[getRootChildren().getItemCount()][cols.length];
    
    for (int i = 0; i < getRootChildren().getItemCount(); i++) {
      for (int j = 0; j < cols.length; j++) {
        data[i][j] = getRootChildren().getItem(i).getRow().getCell(j).getLabel();
      }
    }
    
    table.populateTable(data);
    managedObject = table;
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
    // TODO Auto-generated method stub
    return 0;
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
    this.columns = columns;
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
    // TODO Auto-generated method stub
    
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
