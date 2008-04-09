package org.pentaho.ui.xul.swt;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

public interface TabularWidget {

  public int getItemHeight();
  public void addSelectionListener(SelectionListener listener);
  
  public Composite getComposite();
  
  public Object[][] getValues();
  
  
  
}
