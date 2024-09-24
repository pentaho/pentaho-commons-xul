/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.ui.xul.swt.tags.treeutil;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TableColumnSorter extends XulColumnSorter {

  private TableViewer viewer;

  public TableColumnSorter( Viewer viewer, XulSortProperties properties ) {
    super( viewer, properties );
  }

  @Override
  public void initializeViewer( Viewer viewer ) throws Exception {

    if ( !( viewer instanceof TableViewer ) ) {
      throw new Exception();
    }

    this.viewer = (TableViewer) viewer;
    this.viewer.setComparator( this );

    if ( properties.isSortable() ) {
      for ( TableColumn tableColumn : this.viewer.getTable().getColumns() ) {
        tableColumn.addSelectionListener( selectionHandler );
      }
    }
  }

  @Override
  public Item[] getViewerColumns() {
    return viewer.getTable().getColumns();
  }

  @Override
  public void setViewerColumns() {

    Table table = viewer.getTable();
    switch ( direction ) {
      case ASCENDING:
        table.setSortColumn( (TableColumn) column );
        table.setSortDirection( SWT.UP );
        compareIndex = 1;
        break;
      case DESCENDING:
        table.setSortColumn( (TableColumn) column );
        table.setSortDirection( SWT.DOWN );
        compareIndex = -1;
        break;
      default:
        table.setSortColumn( null );
        table.setSortDirection( SWT.NONE );
        compareIndex = 0;
        break;
    }
    viewer.setComparator( null );
    viewer.setComparator( this );
  }

  protected int doCompare( Viewer v, Object e1, Object e2 ) {
    String t1 = "";
    String t2 = "";
    if ( viewer.getLabelProvider() instanceof XulTableColumnLabelProvider ) {
      XulTableColumnLabelProvider columnLabelProvider = (XulTableColumnLabelProvider) viewer.getLabelProvider();
      t1 = columnLabelProvider.getColumnText( e1, columnIndex );
      t2 = columnLabelProvider.getColumnText( e2, columnIndex );

    } else {
      ILabelProvider labelProvider = (ILabelProvider) viewer.getLabelProvider( columnIndex );
      t1 = labelProvider.getText( e1 );
      t2 = labelProvider.getText( e2 );
    }
    if ( t1 == null ) {
      t1 = "";
    }
    if ( t2 == null ) {
      t2 = "";
    }
    return t1.compareToIgnoreCase( t2 );
  }

}
