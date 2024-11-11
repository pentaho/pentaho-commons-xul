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


package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.TreeCellEditor;
import org.pentaho.ui.xul.util.TreeCellRenderer;

import java.util.Collection;

public interface XulTree extends XulContainer {

  boolean isHierarchical();

  void setDisabled( boolean dis );

  boolean isDisabled();

  void setEditable( boolean edit );

  boolean isEditable();

  void setEnableColumnDrag( boolean drag );

  boolean isEnableColumnDrag();

  void setOnselect( String select );

  String getOnselect();

  void setOnedit( String onedit );

  String getOnedit();

  void setRows( int rows );

  int getRows();

  void setSeltype( String type );

  String getSeltype();

  int getWidth();

  void setColumns( XulTreeCols columns );

  XulTreeCols getColumns();

  XulTreeChildren getRootChildren();

  void setRootChildren( XulTreeChildren rootChildren );

  void setActiveCellCoordinates( int row, int column );

  int[] getActiveCellCoordinates();

  Object[][] getValues();

  int[] getSelectedRows();

  int[] getAbsoluteSelectedRows();

  void setSelectedRows( int[] rows );

  void addTreeRow( XulTreeRow row );

  void removeTreeRows( int[] rows );

  Object getData();

  void setData( Object data );

  void update();

  void clearSelection();

  <T> void setElements( Collection<T> elements );

  <T> Collection<T> getElements();

  Object getSelectedItem();

  void registerCellEditor( String key, TreeCellEditor editor );

  void registerCellRenderer( String key, TreeCellRenderer renderer );

  void setTreeItemExpanded( XulTreeItem item, boolean expanded );

  void setBoundObjectExpanded( Object o, boolean expanded );

  void expandAll();

  void collapseAll();

  <T> Collection<T> getSelectedItems();

  <T> void setSelectedItems( Collection<T> items );

  void setHiddenrootnode( boolean hidden );

  boolean isHiddenrootnode();

  void setCommand( String command );

  String getCommand();

  void setPreserveexpandedstate( boolean preserve );

  boolean isPreserveexpandedstate();

  public void setSortable( boolean sort );

  public boolean isSortable();

  public void setTreeLines( boolean visible );

  public boolean isTreeLines();

  void setPreserveselection( boolean preserve );

  boolean isPreserveselection();

  public void setNewitembinding( String binding );

  public String getNewitembinding();

}
