package org.pentaho.ui.xul.containers;

import java.util.Collection;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.TreeCellEditor;
import org.pentaho.ui.xul.util.TreeCellRenderer;

public interface XulTree extends XulContainer {

  boolean isHierarchical();

  void setDisabled(boolean dis);

  boolean isDisabled();

  void setEditable(boolean edit);

  boolean isEditable();

  void setEnableColumnDrag(boolean drag);

  boolean isEnableColumnDrag();

  void setOnselect(String select);

  String getOnselect();

  void setOnedit(String onedit);

  String getOnedit();

  void setRows(int rows);

  int getRows();

  void setSeltype(String type);

  String getSeltype();

  int getWidth();

  void setColumns(XulTreeCols columns);

  XulTreeCols getColumns();

  XulTreeChildren getRootChildren();

  void setRootChildren(XulTreeChildren rootChildren);

  void setActiveCellCoordinates(int row, int column);

  int[] getActiveCellCoordinates();

  Object[][] getValues();

  int[] getSelectedRows();

  void setSelectedRows(int[] rows);

  void addTreeRow(XulTreeRow row);

  void removeTreeRows(int[] rows);

  Object getData();

  void setData(Object data);

  void update();

  void clearSelection();

  <T> void setElements(Collection<T> elements);

  <T> Collection<T> getElements();

  Object getSelectedItem();
  
  void registerCellEditor(String key, TreeCellEditor editor);
  void registerCellRenderer(String key, TreeCellRenderer renderer);
  
  void expandAll();
  void collapseAll();
}
