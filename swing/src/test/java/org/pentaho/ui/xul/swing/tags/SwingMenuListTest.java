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

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingMenuListTest {

  Document doc = null;
  XulDomContainer container;
  XulMenuList list;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/menulist.xul" );

    doc = container.getDocumentRoot();
    list = (XulMenuList) doc.getElementById( "list" );

  }

  @Test
  public void testGetSelectedItem() throws Exception {
    XulMenuitem item = (XulMenuitem) doc.getElementById( "regions" );

    assertEquals( list.getSelectedItem().toString(), item.getLabel() );
  }

  @Test
  public void testSetSelectedItem() throws Exception {
    XulMenuitem item = (XulMenuitem) doc.getElementById( "sales" );
    list.setSelectedItem( item );
    assertEquals( list.getSelectedItem().toString(), item.getLabel() );
  }

  @Test
  public void testGetElements() throws Exception {
    assertEquals( 3, list.getElements().size() );
  }

  @Test
  public void testGetBinding() throws Exception {
    assertEquals( "label", list.getBinding() );
  }

  @Test
  public void testBindings() throws Exception {
    List<TestClass> items = new ArrayList<TestClass>();
    XulMenuList list2 = (XulMenuList) doc.getElementById( "list2" );

    items.add( new TestClass( "foo" ) );
    items.add( new TestClass( "bar" ) );
    items.add( new TestClass( "baz" ) );
    items.add( new TestClass( "bang" ) );
    list2.setElements( items );

    assertEquals( 4, list2.getElements().size() );
  }

  @Test
  public void testGetSelecetdIndex() throws Exception {
    assertEquals( 1, list.getSelectedIndex() );
  }

  @Test
  public void testSetSelecetdIndex() throws Exception {
    XulMenuitem item = (XulMenuitem) doc.getElementById( "sales" );
    list.setSelectedIndex( 0 );
    assertEquals( list.getSelectedItem().toString(), item.getLabel() );
  }

  public class TestClass {
    private String name;

    public TestClass( String name ) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setName( String name ) {
      this.name = name;
    }

  }
}
