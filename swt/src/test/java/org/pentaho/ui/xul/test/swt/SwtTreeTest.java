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
 * Copyright (c) 2002-2018 Hitachi Vantara.  All rights reserved.
 */

package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtTree;
import org.pentaho.ui.xul.util.ColumnType;

public class SwtTreeTest {

  XulRunner runner = null;

  XulDomContainer container;

  XulTree tree;
  XulTree tree2;
  XulTree tree3;

  Document document;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/treeTest.xul" );

    runner = new SwtXulRunner();
    runner.addContainer( container );
    document = container.getDocumentRoot();
    tree = (XulTree) document.getElementById( "testTable" );
    tree2 = (XulTree) document.getElementById( "testTable2" );
    tree3 = (XulTree) document.getElementById( "testTable3" );

    XulRunner runner = new SwtXulRunner();
    runner.addContainer( container );
  }

  @Test
  public void testRowCount() throws Exception {
    assertEquals( 2, tree.getRootChildren().getItemCount() );
  }

  @Test
  public void testGetCols() throws Exception {
    assertEquals( tree.getColumns(), document.getElementById( "column_list" ) );
  }

  @Test
  public void testGetRootChildren() throws Exception {
    assertEquals( tree.getRootChildren(), document.getElementById( "main_treechildren" ) );
  }

  @Test
  public void testRowType() throws Exception {
    XulTreeCols cols = tree.getColumns();
    assertEquals( cols.getColumn( 0 ).getColumnType(), ColumnType.CHECKBOX );
    assertEquals( cols.getColumn( 1 ).getColumnType(), ColumnType.TEXT );
    assertEquals( cols.getColumn( 3 ).getColumnType(), ColumnType.COMBOBOX );

  }

  @Test
  public void testSelType() throws Exception {
    assertEquals( SwtTree.SELECTION_MODE.MULTIPLE.toString(), tree.getSeltype() );
  }

  @Test
  public void testActiveCellCoordinates() throws Exception {
    tree2.setActiveCellCoordinates( 0, 3 );
    assertTrue( Arrays.equals( tree2.getActiveCellCoordinates(), new int[] { 0, 3 } ) );
  }

  @Test
  public void testGetEnableColumnDrag() throws Exception {
    assertTrue( tree.isEnableColumnDrag() );
    assertTrue( !tree2.isEnableColumnDrag() );
    tree.setEnableColumnDrag( false );
    assertTrue( !tree.isEnableColumnDrag() );
  }

  @Test
  public void testDisabled() throws Exception {
    assertTrue( !tree.isDisabled() );
    tree.setDisabled( true );
    assertTrue( tree.isDisabled() );
  }

  @Ignore
  @Test
  public void testSetElements() {
    List<Person> peeps = new ArrayList<Person>();
    Person perp = new Person();
    perp.enabled = true;
    perp.name = "Wil";
    perp.city = "Narcossee";
    perp.titles = new Vector<String>();
    perp.titles.addAll( Arrays.asList( new String[] { "Blogger", "Actor", "Static coder" } ) );
    perp.disposition = "Feeling it";
    peeps.add( perp );

    perp = new Person();
    perp.enabled = false;
    perp.name = "Mike";
    perp.city = "St Cloud";
    perp.titles = new Vector<String>();
    perp.titles.addAll( Arrays.asList( new String[] { "Add clicker", "Prettiest man in OSS", "GWT ninja" } ) );
    perp.disposition = "Weary";
    peeps.add( perp );
    tree3.setElements( peeps );

    Object[][] sampleData = new Object[2][5];
    sampleData[0] = new Object[] { Boolean.TRUE, "Wil", "Narcossee", "Blogger", "Feeling it" };
    sampleData[1] = new Object[] { Boolean.FALSE, "Mike", "St Cloud", "Add clicker", "Weary" };

    Object[][] vals = tree3.getValues();
    for ( int i = 0; i < sampleData.length; i++ ) {
      for ( int y = 0; y < sampleData[i].length; y++ ) {
        assertEquals( sampleData[i][y], vals[i][y] );
      }
    }

  }

  public class Person extends XulEventSourceAdapter {
    boolean enabled;
    String name;
    String city;
    Vector<String> titles;
    String disposition;

    public Person() {

    }

    public boolean isEnabled() {

      return enabled;
    }

    public void setEnabled( boolean enabled ) {

      this.enabled = enabled;
    }

    public String getName() {

      return name;
    }

    public void setName( String name ) {

      this.name = name;
    }

    public String getCity() {

      return city;
    }

    public void setCity( String city ) {

      this.city = city;
    }

    public Vector<String> getTitles() {

      return titles;
    }

    public void setTitles( Vector<String> titles ) {

      this.titles = titles;
    }

    public String getDisposition() {

      return disposition;
    }

    public void setDisposition( String disposition ) {

      this.disposition = disposition;
    }

  }
}
