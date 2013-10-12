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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags.treeutil;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Item;
import org.pentaho.ui.xul.swt.tags.SwtTreeItem;
import org.pentaho.ui.xul.util.SortDirection;

/**
 * Its unfortunate due to the lack of a base class or common interface between a tree and a table in SWT that we must
 * resort to this random separation of code in an abstract class. The pieces that have been abstracted are those that
 * require us to talk directly to the table or tree to set necessary variables.
 * 
 * @author GMoran
 * 
 */
public abstract class XulColumnSorter extends ViewerComparator {

  SortDirection direction = SortDirection.NATURAL;

  Item column = null;

  int columnIndex = 0;

  int compareIndex = 0;

  Comparator comparable = null;

  XulSortProperties properties;

  protected final SelectionListener selectionHandler = new SelectionAdapter() {
    public void widgetSelected( SelectionEvent e ) {
      Item selectedColumn = (Item) e.widget;
      setColumn( selectedColumn );
    }
  };

  public XulColumnSorter( Viewer viewer, XulSortProperties properties ) {
    this.properties = properties;

    // set comparator on viewer and add selection listeners to viewer's columns
    try {
      initializeViewer( viewer );
    } catch ( Exception e ) {
      e.printStackTrace();
    }

    if ( properties.getSortColumn() != null ) {
      setColumn( properties.getSortColumn() );
    }

  }

  public abstract void initializeViewer( Viewer viewer ) throws Exception;

  public void setColumn( Item selectedColumn ) {
    if ( column == selectedColumn ) {
      switch ( direction ) {
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
      this.direction = properties.getSortDirection();
      comparable = null;
    }

    // set the sort direction and column on the viewer
    setViewerColumns();

    switch ( direction ) {
      case ASCENDING:
        compareIndex = 1;
        break;
      case DESCENDING:
        compareIndex = -1;
        break;
      default:
        compareIndex = 0;
        break;
    }
    Item[] columns = getViewerColumns();
    for ( int i = 0; i < columns.length; i++ ) {
      Item theColumn = columns[i];
      if ( theColumn == this.column ) {
        columnIndex = i;
      }
    }
  }

  @Override
  public int compare( Viewer viewer, Object e1, Object e2 ) {
    SwtTreeItem item = (SwtTreeItem) e1;
    Object o1 = item.getBoundObject();
    item = (SwtTreeItem) e2;
    Object o2 = item.getBoundObject();

    // cache the comparable for future use...
    if ( ( properties.getSortMethod( column ) != null ) && ( comparable == null ) ) {
      Method method;
      try {
        method = o1.getClass().getMethod( properties.getSortMethod( column ) );
        comparable = (Comparator) method.invoke( o1 );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    if ( comparable != null ) {
      return compareIndex * comparable.compare( o1, o2 );
    }

    return compareIndex * doCompare( viewer, e1, e2 );
  }

  public abstract void setViewerColumns();

  public abstract Item[] getViewerColumns();

  protected abstract int doCompare( Viewer v, Object e1, Object e2 );

}
