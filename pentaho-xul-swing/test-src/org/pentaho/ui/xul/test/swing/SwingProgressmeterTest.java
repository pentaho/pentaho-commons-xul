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

package org.pentaho.ui.xul.test.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingProgressmeterTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulProgressmeter determined;
  XulProgressmeter undetermined;

  @Before
  public void setUp() throws Exception {

    container = new SwingXulLoader().loadXul( "resource/documents/progressmeter.xul" );

    runner = new SwingXulRunner();
    runner.addContainer( container );
    determined = (XulProgressmeter) container.getDocumentRoot().getElementById( "determined" );
    undetermined = (XulProgressmeter) container.getDocumentRoot().getElementById( "undetermined" );

  }

  @After
  public void tearDown() throws Exception {
    try {
      runner.stop();
    } catch ( Exception e ) {
    }
  }

  @Test
  public void testGetValue() throws Exception {
    // test initial value from parse
    assertEquals( 10, determined.getValue() );
  }

  @Test
  public void testSetValue() throws Exception {
    determined.setValue( 35 );
    assertEquals( 35, determined.getValue() );
  }

  @Test
  public void testGetMinimum() throws Exception {
    assertEquals( 5, determined.getMinimum() );
  }

  @Test
  public void testGetMaximum() throws Exception {
    assertEquals( 70, determined.getMaximum() );
  }

  @Test
  public void testSetMaximum() throws Exception {
    determined.setMaximum( 65 );
    assertEquals( 65, determined.getMaximum() );
  }

  @Test
  public void testSetMinimum() throws Exception {
    determined.setMinimum( 20 );
    assertEquals( 20, determined.getMinimum() );
  }

  @Test
  public void testIsDeterminate() throws Exception {
    assertTrue( !determined.isIndeterminate() );
    assertTrue( undetermined.isIndeterminate() );
  }

  @Test
  public void testSetDeterminate() throws Exception {
    assertTrue( undetermined.isIndeterminate() );
    undetermined.setIndeterminate( false );
    assertTrue( !undetermined.isIndeterminate() );
  }
}
