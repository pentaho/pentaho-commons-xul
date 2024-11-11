/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

import java.util.Collection;

/**
 * The interface for the XUL listbox widget. The listbox can be single select or multi-select and can have
 * scrollbars.
 * 
 * @author nbaker
 * 
 */
public interface XulListbox extends XulContainer {

  public enum SEL_TYPE {
    SINGLE, MULTIPLE
  };

  /**
   * Adds a list item object to the list. Note that specific implementations may limit the object class types
   * allowed to be passed in.
   * 
   * @param item
   */
  public void addItem( Object item );

  /**
   * Clears all list items from the listbox.
   */
  public void removeItems();

  /**
   * 
   * @param dis
   *          If true, disable this button. Otherwise, attribute should be removed.
   */
  public void setDisabled( boolean dis );

  /**
   * 
   * @param dis
   *          If true, disable this button. Otherwise, attribute should be removed.
   */
  public void setDisabled( String dis );

  /**
   * XUL's attribute is "disabled", thus this acts exactly the opposite of SWT/Swing/AWT. If the property is not
   * available, then the control is enabled.
   * 
   * @return boolean True if the control is disabled.
   */
  public boolean isDisabled();

  /**
   * 
   * @return The number of rows displayed in the listbox. This is not the same as the total number of rows int he
   *         listbox, which you can retrieve with getRowCount().
   * 
   */
  public int getRows();

  /**
   * The number of rows to display in the list box. If the listbox contains more than this many rows, a scrollbar
   * will appear which the user can use to scroll to the other rows. To get the actual number of rows in the
   * listbox, use the getRowCount method.
   * 
   * @param rows
   *          The number of rows to display in the listbox.
   * 
   */
  public void setRows( int rows );

  /**
   * The total number of listitems in the listbox.
   * 
   * @return The listbox's total number of rows.
   * 
   */
  public int getRowCount();

  /**
   * 
   * @return The selection mode for this listbox; either single or multiple.
   * 
   */
  public String getSeltype();

  /**
   * Used to indicate whether multiple selection is allowed.
   * 
   * single: Only one row may be selected at a time. multiple: Multiple rows may be selected at once.
   * 
   * @param selType
   *          Valid values are single or multiple.
   */
  public void setSeltype( String selType );

  /**
   * 
   * @return The single (or first) item selected in the listbox.
   */
  public Object getSelectedItem();

  /**
   * 
   * @param item
   *          Set the given item as selected in the listbox.
   */
  public void setSelectedItem( Object item );

  /**
   * 
   * @param index
   *          Set the given index as selected in the listbox.
   */
  public void setSelectedIndex( int index );

  /**
   * 
   * @return if the list is a multiple selection listbox, then return the list of selected items. If single select,
   *         this will be a list of one, and you should use the getSelectedItem() method instead.
   * 
   */
  public Object[] getSelectedItems();

  /**
   * For a multiple selection listbox, this method sets the items in the list as selected in the listbox.
   * 
   * @param items
   *          The items to select.
   */
  public void setSelectedItems( Object[] items );

  /**
   * Sets the method that will be invoked when an item in this list is selected. Also hooks up any listeners for
   * this event.
   * 
   * @param method
   *          the method to execute when the item is selected.
   */
  public void setOnselect( String method );

  /**
   * 
   * @return the method string used for this list's selection event.
   */
  public String getOnselect();

  /**
   * Sets the name of the method to be invoked when a user double-clicks an item in the list.
   * 
   * @param command
   */
  public void setCommand( String command );

  public String getCommand();

  /**
   * Helper method returning the zero based index of selected item.
   * 
   * @return The selected item's index.
   */
  public int getSelectedIndex();

  /**
   * Helper method returning the zero based indices of selected items.
   * 
   * @return The selected items' indices as an array of native int values.
   */
  public int[] getSelectedIndices();

  public void setSelectedIndices( int[] indices );

  <T> void setElements( Collection<T> elements );

  <T> Collection<T> getElements();

  public void setBinding( String binding );

  public String getBinding();

}
