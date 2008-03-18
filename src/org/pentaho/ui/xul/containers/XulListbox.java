package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulListbox extends XulContainer {
  
  public void addItem(Object item);
  public boolean isDisabled();
  public void setDisabled(boolean dis);
  public int getRows();
  public void setRows(int rows);
  public boolean isSeltype();
  public void setSeltype(boolean selType);

}
