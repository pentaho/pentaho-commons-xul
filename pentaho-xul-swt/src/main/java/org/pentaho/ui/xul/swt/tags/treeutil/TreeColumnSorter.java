/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

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
