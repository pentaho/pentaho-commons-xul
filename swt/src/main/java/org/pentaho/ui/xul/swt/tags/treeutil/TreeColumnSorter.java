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
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class TreeColumnSorter extends XulColumnSorter {

  private TreeViewer viewer;

  public TreeColumnSorter( TreeViewer viewer, XulSortProperties properties ) {
    super( viewer, properties );
  }

  public void initializeViewer( Viewer viewer ) throws Exception {

    if ( !( viewer instanceof TreeViewer ) ) {
      throw new Exception();
    }

    this.viewer = (TreeViewer) viewer;
    this.viewer.setComparator( this );

    if ( properties.isSortable() ) {
      for ( TreeColumn tableColumn : this.viewer.getTree().getColumns() ) {
        tableColumn.addSelectionListener( selectionHandler );
      }
    }
  }

  @Override
  public void setViewerColumns() {
    Tree tree = viewer.getTree();
    switch ( direction ) {
      case ASCENDING:
        tree.setSortColumn( (TreeColumn) column );
        tree.setSortDirection( SWT.UP );
        compareIndex = 1;
        break;
      case DESCENDING:
        tree.setSortColumn( (TreeColumn) column );
        tree.setSortDirection( SWT.DOWN );
        compareIndex = -1;
        break;
      default:
        tree.setSortColumn( null );
        tree.setSortDirection( SWT.NONE );
        compareIndex = 0;
        break;
    }

    viewer.setComparator( null );
    viewer.setComparator( this );
  }

  @Override
  protected int doCompare( Viewer v, Object e1, Object e2 ) {
    ILabelProvider labelProvider = (ILabelProvider) viewer.getLabelProvider( columnIndex );
    String t1 = labelProvider.getText( e1 );
    String t2 = labelProvider.getText( e2 );
    if ( t1 == null ) {
      t1 = "";
    }
    if ( t2 == null ) {
      t2 = "";
    }
    return t1.compareToIgnoreCase( t2 );
  }

  @Override
  public Item[] getViewerColumns() {
    return viewer.getTree().getColumns();
  }
}
