package org.pentaho.ui.xul.util;

public interface TreeCellEditor {

  public void setValue(Object val);
  
  public Object getValue();
  
  public void show(int row, int col, Object boundObj, String columnBinding, TreeCellEditorCallback callback);
  
  public void hide();
}
