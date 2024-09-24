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

import java.util.Comparator;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.eclipse.swt.widgets.Item;
import org.pentaho.ui.xul.util.SortDirection;

/**
 * This class accommodates the relationships between sort properties for the XUL Tree widget and the XUL Table widget.
 * 
 * 1. In both widgets, only one column can have an active sort at a time, and the direction applies to that active sort.
 * The corollary is that there can be no active sorts. You can hook up listeners through the xxxColumnSorters to
 * activate sort from the UI on a table that has been initialized with no active sort (not applicable to trees). Thus,
 * sortActive and sortDirection apply to the sortColumn member variable.
 * 
 * 2. The sortable property applies to the tree and the table at the highest level (XULTree). If sortable is false,
 * nothing is initialized sorted and nothing can be sorted. If sortable is true, then the columns CAN be sorted, but
 * whether they are initialized sorted depends on the member variables described in point 1.
 * 
 * 3. Every column (XulTreeCol) can have a custom sort method defined (pen:comparatorbinding) that returns a Comparator
 * to use when sorting that column. This sort method must exist on the SwtTreeItem's bound domain object
 * (treeItem.getBoundObject()). Note that this does not change the fact that only one column at a time can be sorted; it
 * only means that the sort algorithm can be overridden for all columns.
 * 
 * @author GMoran
 * 
 */
public class XulSortProperties {

  private boolean sortable = false;

  /**
   * org.eclipse.swt.widgets.Item is the base class to TableColumn and treeColumn
   */
  private Item sortColumn = null;

  private boolean sortActive = false;

  private SortDirection sortDirection = SortDirection.NATURAL;

  private Comparator activeComparator = null;

  private Map sortMethods = new HashedMap();

  public boolean isSortable() {
    return sortable;
  }

  public void setSortable( boolean sortable ) {
    this.sortable = sortable;
  }

  public Item getSortColumn() {
    return sortColumn;
  }

  public void setSortColumn( Item sortColumn ) {
    this.sortColumn = sortColumn;
  }

  public boolean isSortActive() {
    return sortActive;
  }

  public void setSortActive( boolean sortActive ) {
    this.sortActive = sortActive;
  }

  public SortDirection getSortDirection() {
    return sortDirection;
  }

  public void setSortDirection( SortDirection sortDirection ) {
    this.sortDirection = sortDirection;
  }

  public Map getSortMethods() {
    return sortMethods;
  }

  public void setSortMethods( Map sortMethods ) {
    this.sortMethods = sortMethods;
  }

  public void setSortMethod( Item column, String method ) {
    sortMethods.put( column, method );
  }

  public String getSortMethod( Item column ) {
    return (String) sortMethods.get( column );
  }

  public Comparator getActiveComparator() {
    return activeComparator;
  }

  public void setActiveComparator( Comparator activeComparator ) {
    this.activeComparator = activeComparator;
  }

}
