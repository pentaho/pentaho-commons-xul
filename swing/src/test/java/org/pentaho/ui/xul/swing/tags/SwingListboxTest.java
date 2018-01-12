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

package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingListboxTest {
  Document doc = null;

  XulDomContainer container;

  XulListbox list;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/listbox.xul" );

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
    assertEquals( list.getSelectedItem(), item );
  }

  @Test
  public void testGetSelectedItems() throws Exception {
    list.setSelectedIndex( 2 );
    XulListitem item = (XulListitem) doc.getElementById( "listItem2" );
    Object[] selectedItems = list.getSelectedItems();
    assertEquals( 1, selectedItems.length );
    assertEquals( selectedItems[0], item );
  }

  @Test
  public void testSetSelectedItem() throws Exception {
    assertEquals( 1, list.getSelectedIndex() );
    XulListitem item = (XulListitem) doc.getElementById( "listItem2" );
    list.setSelectedItem( item );
    assertEquals( 2, list.getSelectedIndex() );
  }

  @Test
  public void testSetSelectedIndices() {
    int[] indices = { 0, 2 };
    list.setSeltype( "multiple" );
    list.setSelectedIndices( indices );
    assertTrue( "selected indices should be equal: " + toString( indices ) + " vs "
        + toString( list.getSelectedIndices() ), Arrays.equals( indices, list.getSelectedIndices() ) );
  }

  private static String toString( int[] is ) {
    StringBuilder builder = new StringBuilder();
    builder.append( '{' );
    for ( int i : is ) {
      builder.append( i );
      builder.append( ',' );
    }
    builder.append( '}' );
    return builder.toString();
  }

  @Test
  public void testSetSelectedIndicesByItem() throws Exception {
    assertEquals( 1, list.getSelectedIndex() );
    list.setSeltype( "multiple" );
    Object[] items = new Object[] { doc.getElementById( "listItem2" ), doc.getElementById( "listItem1" ) };
    list.setSelectedItems( items );
    int[] selected = list.getSelectedIndices();
    assertEquals( 2, selected.length );
    assertEquals( 1, selected[0] );
    assertEquals( 2, selected[1] );
  }

  @Test
  public void testSetSelectedItems() throws Exception {
    assertEquals( 1, list.getSelectedIndex() );
    list.setSeltype( "multiple" );

    XulListitem item2 = (XulListitem) doc.getElementById( "listItem2" );
    XulListitem item1 = (XulListitem) doc.getElementById( "listItem1" );
    Object[] items = new Object[] { item2, item1 };
    list.setSelectedItems( items );

    Object[] selected = list.getSelectedItems();
    assertEquals( item1, selected[0] );
    assertEquals( item2, selected[1] );
  }

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

  @Test
  public void testSetElements() throws Exception {
    XulListbox box = (XulListbox) doc.getElementById( "listbox2" );
    List<Person> peeps = new ArrayList<Person>();
    peeps.add( new Person( "Nick" ) );
    peeps.add( new Person( "Barb" ) );
    peeps.add( new Person( "Kenneth" ) );

    box.setElements( peeps );

    assertEquals( 3, box.getRowCount() );

    List<XulComponent> items = box.getChildNodes();
    assertEquals( "Nick", ( (XulListitem) items.get( 0 ) ).getLabel() );
    assertEquals( "Barb", ( (XulListitem) items.get( 1 ) ).getLabel() );
    assertEquals( "Kenneth", ( (XulListitem) items.get( 2 ) ).getLabel() );

  }

  public class Person {
    private String firstName;

    public Person( String firstName ) {
      this.firstName = firstName;
    }

    public String getFirstName() {

      return firstName;
    }

    public void setFirstName( String firstName ) {

      this.firstName = firstName;
    }

  }

}
