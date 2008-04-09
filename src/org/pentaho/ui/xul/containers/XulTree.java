package org.pentaho.ui.xul.containers;

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

}
