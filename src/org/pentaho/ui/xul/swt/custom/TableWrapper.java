package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.TabularWidget;

public class TableWrapper implements TabularWidget {

  private Table table; 
  
  final XulTree owner;
  
  public TableWrapper(TableSelection selection, Composite parent, XulTree owner){
    
    this.owner = owner;
    
    table = new Table(parent, selection.getSwtStyle()); 
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    table.addMouseListener(new TableMouseAdapter());
    
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
  
  /* ================================================================================ */
  
  // Table listens to the mouse, to determine what cells to edit

  private class TableMouseAdapter extends MouseAdapter {

    public void mouseDown(MouseEvent event) {
      if (event.button == 1) {
        boolean shift = (event.stateMask & SWT.SHIFT) != 0;
        boolean control = (event.stateMask & SWT.CONTROL) != 0;
        if (!shift && !control) {
          Rectangle clientArea = table.getClientArea();
          Point pt = new Point(event.x, event.y);
          int index = table.getTopIndex();
          while (index < table.getItemCount()) {
            boolean visible = false;
            final TableItem item = table.getItem(index);
            for (int i = 0; i < table.getColumnCount(); i++) {
              Rectangle rect = item.getBounds(i);
              if (rect.contains(pt)) {
                
                XulTreeCol column = owner.getColumns().getColumn(i);
                if (column.isEditable()){
                  XulTreeItem rowItem = owner.getRootChildren().getItem(index);
                  rowItem.getRow().makeCellEditable(i);
                }
                //activeTableItem = item;
                //activeTableColumn = i;
                //activeTableRow = index;

                //editSelected();
                return;
              } else {
                if (i == table.getColumnCount() - 1 && // last column 
                    pt.x > rect.x + rect.width && // to the right 
                    pt.y >= rect.y && pt.y <= rect.y + rect.height // same height as this visible item
                ) {
                  return; // don't do anything when clicking to the right of the grid.
                }
              }
              if (!visible && rect.intersects(clientArea)) {
                visible = true;
              }
            }
            if (!visible)
              return;
            index++;
          }
          XulTreeRow row = owner.getRootChildren().addNewRow();
          row.makeCellEditable(0);
          // OK, so they clicked in the table and we did not go into the invisible: below the last line!
          // Position on last row, 1st column and add a new line...

          //setPosition(table.getItemCount() - 1, 1);
          //insertRowAfter();
        }
      }
    }
    
  }

  


}
