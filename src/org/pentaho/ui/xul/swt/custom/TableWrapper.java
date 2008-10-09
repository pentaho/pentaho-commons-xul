package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.TabularWidget;

public class TableWrapper implements TabularWidget {

  private Table table;

  final XulTree owner;

  public TableWrapper(TableSelection selection, Composite parent, XulTree owner) {

    this.owner = owner;

    table = new Table(parent, selection.getSwtStyle()|SWT.BORDER);
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    table.setDragDetect(false);
    

    table.addMouseListener(new TableMouseAdapter());
    table.addKeyListener(new TableKeyAdapter());

    TraverseListener lsTraverse = new TraverseListener() {
      public void keyTraversed(TraverseEvent e) {
        e.doit = false;
      }
    };
    table.addTraverseListener(lsTraverse);

  }

  public void addSelectionListener(SelectionListener listener) {
    table.addSelectionListener(listener);
  }

  public int getItemHeight() {
    return table.getItemHeight();
  }

  public Composite getComposite() {
    return table;
  }
  
  public Object[][] getValues(){
    Object [][] values = null;

    int columnCount = table.getColumnCount();
    int rowCount = table.getItemCount();
    
    if ((columnCount <= 0) || (rowCount <=0)){
      return new String[0][0];
    }
    
    values = new Object[rowCount][columnCount];
    
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < columnCount; j++) {
      	switch(this.owner.getColumns().getColumn(j).getColumnType()){
      		case CHECKBOX:
      			values[i][j] = Boolean.valueOf(table.getItem(i).getText(j));
      			break;
      		default:
      			values[i][j] = table.getItem(i).getText(j);
      			break;
      	}
      }
    }
    
    return values;
  }
  
  public int getSelectionIndex(){
    return table.getSelectionIndex();
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
          int row = table.getTopIndex();

          while (row < table.getItemCount()) {
            boolean visible = false;
            final TableItem item = table.getItem(row);
            for (int col = 0; col < table.getColumnCount(); col++) {
              Rectangle rect = item.getBounds(col);
              if (rect.contains(pt)) {

                owner.setActiveCellCoordinates(row, col);
                XulTreeItem rowItem = owner.getRootChildren().getItem(row);
                rowItem.getRow().makeCellEditable(col);
                return;

              } else {
                if (col == table.getColumnCount() - 1 && // last column 
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
            row++;
          }
          // OK, so they clicked in the table and we did not go into the invisible: below the last line!
          // Position on last row, 1st column and add a new line...

          if (owner.isEditable()){
            XulTreeRow treeRow = owner.getRootChildren().addNewRow();
            treeRow.makeCellEditable(-1);
            owner.setActiveCellCoordinates(row, 0);
          }
        }
      }
    }

  }

  /* ================================================================================ */

  // Table responds to keys as well...
  private class TableKeyAdapter extends KeyAdapter {
    public void keyPressed(KeyEvent e) {

      int activeRow = owner.getActiveCellCoordinates()[0];
      int activeCol = owner.getActiveCellCoordinates()[1];

      if ((activeRow < 0) || (activeCol < 0))
        return;

      int maxcols = table.getColumnCount();
      int maxrows = table.getItemCount();

      boolean shift = (e.stateMask & SWT.SHIFT) != 0;
      
      if (e.keyCode == SWT.DEL){
        
        if (!owner.isEditable()){
          return;
        }
          
        XulTreeItem rowItem = owner.getRootChildren().getItem(activeRow);
        if (rowItem != null){
         owner.getRootChildren().removeItem(activeRow);
          activeRow = activeRow - 1; 
          owner.setActiveCellCoordinates(activeRow, activeCol);
          table.deselectAll();
          table.select(activeRow);
        }
      }

      // Move cursor: set selection on the row in question.       
      if ((e.keyCode == SWT.ARROW_DOWN && !shift) || (e.keyCode == SWT.ARROW_UP && !shift)
          || (e.keyCode == SWT.HOME && !shift) || (e.keyCode == SWT.END && !shift)) {
        switch (e.keyCode) {
          case SWT.ARROW_DOWN:
            activeRow++;
            if (activeRow >= maxrows) {
              if (owner.isEditable()) {
                XulTreeRow row = owner.getRootChildren().addNewRow();
                row.makeCellEditable(-1);
              } else {
                activeRow = maxrows - 1;
              }
            }
            break;
          case SWT.ARROW_UP:
            activeRow--;
            if (activeRow < 0)
              activeRow = 0;
            break;
          case SWT.HOME:
            activeRow = 0;
            break;
          case SWT.END:
            activeRow = maxrows - 1;
            break;
          default:
            break;
        }

        owner.setActiveCellCoordinates(activeRow, activeCol);
        table.deselectAll();
        table.select(activeRow);
        return;
      }

      // Return: edit the first field in the row.
      if (e.keyCode == SWT.CR || e.keyCode == SWT.ARROW_RIGHT || e.keyCode == SWT.TAB) {
        activeCol = 0;
        owner.setActiveCellCoordinates(activeRow, activeCol);
        XulTreeItem rowItem = owner.getRootChildren().getItem(activeRow);
        rowItem.getRow().makeCellEditable(activeCol);
        return;
      }

      if (activeCol > 0) {

        if (e.character == SWT.TAB) {
          // TAB
          if (e.keyCode == SWT.TAB && ((e.stateMask & SWT.SHIFT) == 0)) {
            activeCol++;
          }
          // Shift Tab
          if (e.keyCode == SWT.TAB && ((e.stateMask & SWT.SHIFT) != 0)) {
            activeCol--;
          }
          if (activeCol < 1) // from SHIFT-TAB
          {
            activeCol = maxcols - 1;
            if (activeRow > 0)
              activeRow--;
          }
          if (activeCol >= maxcols) // from TAB
          {
            activeCol = 1;
            activeRow++;

          }
          // Tab beyond last line: add a line to table!
          if (activeRow >= maxrows) {
            XulTreeRow row = owner.getRootChildren().addNewRow();
          }
            XulTreeItem rowItem = owner.getRootChildren().getItem(activeRow);
            rowItem.getRow().makeCellEditable(activeCol);
        }
      }
      owner.setActiveCellCoordinates(activeRow, activeCol);
      table.setFocus();
    }
  }

}
