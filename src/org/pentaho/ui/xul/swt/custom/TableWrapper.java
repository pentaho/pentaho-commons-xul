package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.TabularWidget;

public class TableWrapper implements TabularWidget {

  private Table table; 
  
  public TableWrapper(TableSelection selection, Composite parent){
    table = new Table(parent, selection.getSwtStyle()); 
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
  }
  
  public void addSelectionListener(SelectionListener listener) {
    table.addSelectionListener(listener);
  }

  public int getItemHeight() {
    return table.getItemHeight();
  }
  
  public Composite getComposite(){
    return table;
  }


}
