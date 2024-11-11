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


package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;

public class SwtListboxTest {
  Document doc = null;
  XulDomContainer container;
  XulListbox list;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/listbox.xul" );

    doc = container.getDocumentRoot();
    list = (XulListbox) doc.getElementById( "listbox" );

  }

  @Test
  public void testDisabled() throws Exception {
    assertTrue( !list.isDisabled() );
    list.setDisabled( true );
    assertTrue( list.isDisabled() );
    list.setDisabled( "false" );
    assertTrue( list.isDisabled() == false );
    list.setDisabled( "true" );
    assertTrue( list.isDisabled() );
  }

  @Test
  public void testRows() throws Exception {
    assertEquals( 5, list.getRows() );
    list.setRows( 6 );
    assertEquals( 6, list.getRows() );
  }

  @Test
  public void testSeltype() throws Exception {
    assertEquals( "single", list.getSeltype() );

  }

  @Test
  public void testOnselect() throws Exception {
    assertEquals( "handler.consume()", list.getOnselect() );

  }

  @Test
  public void testSelectedIndex() throws Exception {
    list.setSelectedIndex( 2 );
    assertEquals( 2, list.getSelectedIndex() );
  }

  @Test
  public void testGetSelectedValue() throws Exception {
    list.setSelectedIndex( 2 );
    XulListitem item = (XulListitem) doc.getElementById( "listItem2" );
    assertEquals( item.getLabel(), list.getSelectedItem() );
  }

  @Test
  public void testGetSelectedItems() throws Exception {
    list.setSelectedIndex( 2 );
    XulListitem item = (XulListitem) doc.getElementById( "listItem2" );
    Object[] selectedItems = list.getSelectedItems();
    assertEquals( 1, selectedItems.length );
    assertEquals( selectedItems[0], item.getLabel() );
  }

  // @TODO fix item tests in SwtListbox
  // @Test
  // public void testSetSelectedItem() throws Exception {
  // assertEquals(1, list.getSelectedIndex());
  //
  // XulListitem item = (XulListitem) doc.getElementById("listItem2");
  // list.setSelectedItem(item);
  // assertEquals(2, list.getSelectedIndex());
  // }
  //

  // @Test
  // public void testSetSelectedIdexes() throws Exception {
  // assertEquals(1, list.getSelectedIndex());
  // list.setSeltype("multiple");
  // Object[] items = new Object[]{doc.getElementById("listItem2"), doc.getElementById("listItem1")};
  // list.setSelectedItems(items);
  // int[] selected = list.getSelectedIndices();
  // assertEquals(2, selected.length);
  // assertEquals(1, selected[0]);
  // assertEquals(2, selected[1]);
  // }

  // @Test
  // public void testSetSelectedItems() throws Exception {
  // assertEquals(1, list.getSelectedIndex());
  // list.setSeltype("multiple");
  //
  //
  // XulListitem item2 = (XulListitem) doc.getElementById("listItem2");
  // XulListitem item1 = (XulListitem) doc.getElementById("listItem1");
  // Object[] items = new Object[]{item2, item1};
  // list.setSelectedItems(items);
  //
  // Object[] selected = list.getSelectedItems();
  // assertEquals(item1, selected[0]);
  // assertEquals(item2, selected[1]);
  // }

  @Test
  public void testgetRowCount() throws Exception {
    assertEquals( 3, list.getRowCount() );

  }

  @Test
  public void testRemoveItems() throws Exception {
    assertEquals( 3, list.getRowCount() );
    list.removeItems();
    assertEquals( 0, list.getRowCount() );
  }

  @Test
  public void testItemLabel() throws Exception {
    XulListitem item1 = (XulListitem) doc.getElementById( "listItem1" );
    assertEquals( "ODBC", item1.getLabel() );
    item1.setLabel( "foo" );
    assertEquals( "foo", item1.getLabel() );
  }

  @Test
  public void testItemValue() throws Exception {
    XulListitem item1 = (XulListitem) doc.getElementById( "listItem1" );
    item1.setValue( "bar" );
    assertEquals( "bar", item1.getValue() );
  }
}
