package org.pentaho.ui.xul.containers;

import java.util.Collection;

import org.pentaho.ui.xul.XulContainer;

public interface XulTree extends XulContainer {
  
  public boolean isHierarchical();

  public void setDisabled(boolean dis);
  public boolean isDisabled();
  
  public void setEditable(boolean edit);
  public boolean isEditable();
  
  public void setEnableColumnDrag(boolean drag);
  public boolean isEnableColumnDrag();
  
  public void setOnselect(String select);
  public String getOnselect();
  
  public void setOnedit(String onedit);
  public String getOnedit();
  
  public void setRows(int rows);
  public int getRows();
  
  public void setSeltype(String type);
  public String getSeltype();
  
  public int getWidth();
  
  public void setColumns(XulTreeCols columns);
  public XulTreeCols getColumns();

  public XulTreeChildren getRootChildren() ;
  public void setRootChildren(XulTreeChildren rootChildren) ;
  
  public void setActiveCellCoordinates(int row, int column);
  public int[] getActiveCellCoordinates();
  
  public Object[][] getValues();
 
  public int[] getSelectedRows();
  
  public void setSelectedRows(int[] rows);
  
  public void addTreeRow(XulTreeRow row);
  public void removeTreeRows(int[] rows);
  
  public Object getData();
  public void setData(Object data);

  public void update();
  
  public void clearSelection();
  
  public void setElements(Collection<?> elements);
  public Collection<?> getElements();

}
