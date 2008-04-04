package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.swt.RowWidget;
import org.pentaho.ui.xul.util.ColumnType;

public class TableItemWrapper implements RowWidget {

  
  TableItem item = null;
  Table parentTable = null;
  XulTree parentTree = null;
  
  public TableItemWrapper(XulTree parent){

    parentTree = parent;
    parentTable = (Table)parent.getManagedObject();
    item = new TableItem(parentTable, SWT.NONE);

    XulTreeCols columns = parentTree.getColumns();
    for (int i = 0; i < columns.getColumnCount(); i++) {
      XulTreeCol column = columns.getColumn(i);
      if (column.isEditable()){
        makeCellEditable(column.getColumnType(), i);
      }
      
    }
  }
  
  private void makeCellEditable(ColumnType type, int index){

    TableEditor editor = new TableEditor(parentTable);

    switch (type){
      case CHECKBOX:
        Button check = new Button(parentTable, SWT.CHECK);
        editor.setEditor(check, item, index);
        editor.grabHorizontal=true;
        break;
      case TEXT:
        Text edit = new Text(parentTable, SWT.NONE);
        editor.setEditor(edit, item, index);
        editor.grabHorizontal=true;
        break;
      case PROGRESSMETER:
        // TODO Log not supported yet... 
        break;
    }
  }

  public Item getItem() {
    return item;
  }

  public void setText(int index, String text) {
    item.setText(index, text);
  }

}
