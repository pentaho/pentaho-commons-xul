package org.pentaho.ui.xul.swt.tags.treeutil;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.pentaho.ui.xul.util.SortDirection;

public class TableColumnSorter extends ViewerComparator {

  private SortDirection direction = SortDirection.NATURAL;
  private TableColumn column = null;
  private int columnIndex = 0;
  private int compareIndex = 0;
  final private TableViewer viewer;
  private SortDirection sortDirection = SortDirection.NATURAL;

  final private SelectionListener selectionHandler = new SelectionAdapter() {
    public void widgetSelected(SelectionEvent e) {
      TableColumn selectedColumn = (TableColumn) e.widget;
      setColumn(selectedColumn);
    }
  };

  public TableColumnSorter(TableViewer viewer, TableColumn column, SortDirection sortDir, boolean sortable) {
    this.viewer = viewer;
    sortDirection = sortDir;
    viewer.setComparator(this);

    if (column!=null){
      setColumn(column);
    }
    
    if (sortable){
      for (TableColumn tableColumn : viewer.getTable().getColumns()) {
        tableColumn.addSelectionListener(selectionHandler);
      }
    }
  }

  public void setColumn(TableColumn selectedColumn) {
    if (column == selectedColumn) {
      switch (direction) {
      case ASCENDING:
        direction = SortDirection.DESCENDING;
        break;
      case DESCENDING:
        direction = SortDirection.ASCENDING;
        break;
      default:
        direction = SortDirection.ASCENDING;
        break;
      }
    } else {
      this.column = selectedColumn;
      this.direction = sortDirection;
    }

    Table table = viewer.getTable();
    switch (direction) {
    case ASCENDING:
      table.setSortColumn(selectedColumn);
      table.setSortDirection(SWT.UP);
      compareIndex = 1;
      break;
    case DESCENDING:
      table.setSortColumn(selectedColumn);
      table.setSortDirection(SWT.DOWN);
      compareIndex = -1;
      break;
    default:
      table.setSortColumn(null);
      table.setSortDirection(SWT.NONE);
      compareIndex = 0;
      break;
    }
    TableColumn[] columns = table.getColumns();
    for (int i = 0; i < columns.length; i++) {
      TableColumn theColumn = columns[i];
      if (theColumn == this.column) columnIndex = i;
    }
    viewer.setComparator(null);
    viewer.setComparator(this);
  }

  @Override
  public int compare(Viewer viewer, Object e1, Object e2) {
    return compareIndex * doCompare(viewer, e1, e2);
  }

  protected int doCompare(Viewer v, Object e1, Object e2) {
    String t1 = "";
    String t2 = "";
    if (viewer.getLabelProvider() instanceof XulTableColumnLabelProvider){
      XulTableColumnLabelProvider columnLabelProvider = (XulTableColumnLabelProvider)viewer.getLabelProvider();
      t1 = columnLabelProvider.getColumnText(e1,columnIndex);
      t2 = columnLabelProvider.getColumnText(e2,columnIndex);
      
    }else{
      ILabelProvider labelProvider = (ILabelProvider) viewer.getLabelProvider(columnIndex);
      t1 = labelProvider.getText(e1);
      t2 = labelProvider.getText(e2);
    }
    if (t1 == null) t1 = "";
    if (t2 == null) t2 = "";
    return t1.compareToIgnoreCase(t2);
  }
}
