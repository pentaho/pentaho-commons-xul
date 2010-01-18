package org.pentaho.ui.xul.swt.tags.treeutil;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.pentaho.ui.xul.util.SortDirection;

public class TreeColumnSorter extends ViewerComparator {

  private SortDirection direction = SortDirection.ASCENDING;
  private TreeColumn column = null;
  private int columnIndex = 0;
  private int compareIndex = 0;
  final private TreeViewer viewer;
  private SortDirection sortDirection = SortDirection.ASCENDING;

  final private SelectionListener selectionHandler = new SelectionAdapter() {
    public void widgetSelected(SelectionEvent e) {
      TreeColumn selectedColumn = (TreeColumn) e.widget;
      setColumn(selectedColumn);
    }
  };

  public TreeColumnSorter(TreeViewer viewer, TreeColumn column, SortDirection sortDir, boolean sortable) {
    this.viewer = viewer;
    sortDirection = sortDir;
    viewer.setComparator(this);

    if (column!=null){
      setColumn(column);
    }
    
    if (sortable){
      for (TreeColumn tableColumn : viewer.getTree().getColumns()) {
        tableColumn.addSelectionListener(selectionHandler);
      }
    }
  }

  public void setColumn(TreeColumn selectedColumn) {
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
    Tree tree = viewer.getTree();
    //Table table = viewer.getTable();
    switch (direction) {
    case ASCENDING:
      tree.setSortColumn(selectedColumn);
      tree.setSortDirection(SWT.UP);
      compareIndex = 1;
      break;
    case DESCENDING:
      tree.setSortColumn(selectedColumn);
      tree.setSortDirection(SWT.DOWN);
      compareIndex = -1;
      break;
    default:
      tree.setSortColumn(null);
      tree.setSortDirection(SWT.NONE);
      compareIndex = 0;
      break;
    }

    TreeColumn[] columns = tree.getColumns();
    for (int i = 0; i < columns.length; i++) {
      TreeColumn theColumn = columns[i];
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
    ILabelProvider labelProvider = (ILabelProvider) viewer.getLabelProvider(columnIndex);
    String t1 = labelProvider.getText(e1);
    String t2 = labelProvider.getText(e2);
    if (t1 == null) t1 = "";
    if (t2 == null) t2 = "";
    return t1.compareToIgnoreCase(t2);
  }
}
