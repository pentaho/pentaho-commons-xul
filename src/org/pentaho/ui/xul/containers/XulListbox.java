package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulListbox extends XulContainer {
  
  public void addItem(Object item);
  public void removeItems();
  public boolean isDisabled();
  public void setDisabled(boolean dis);
  public int getRows();
  public void setRows(int rows);
  public boolean isSeltype();
  public void setSeltype(boolean selType);
  public Object getSelectedItem();
  public void setSelectedItem(Object item);
  public Object[] getSelectedItems();
  public void setSelectedItems(Object[] items);
  public void setOnselect(String method);
  public String getOnselect();

}
