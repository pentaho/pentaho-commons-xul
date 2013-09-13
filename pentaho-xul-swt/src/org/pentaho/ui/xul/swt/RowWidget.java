package org.pentaho.ui.xul.swt;

import org.eclipse.swt.widgets.Item;

public interface RowWidget {
  
  public static final int EVENT_SELECT = 123;

  public Item getItem();
  
  public void setText(int index, String text);
  
  public void makeCellEditable(int index);
  
  public void remove();
  

}
