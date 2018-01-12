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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingXulRunnerTest {

  XulRunner runner = null;
  Document doc = null;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream( "documents/sampleXul.xul" );
    assertNotNull( "XUL input not found.", in );
    SAXReader rdr = new SAXReader();
    final Document doc = rdr.read( in );

    XulDomContainer container = new SwingXulLoader().loadXul( doc );

    runner = new SwingXulRunner();
    runner.addContainer( container );

  }

  @After
  public void tearDown() throws Exception {
    if ( runner != null ) {
      runner.stop();
    }
  }

  @Test
  public final void testGetXulDomContainers() {
    assertNotNull( "Runner's dom container collection is empty.", runner.getXulDomContainers() );

  }

  @Test
  public final void testInitialize() {
    try {
      runner.initialize();
    } catch ( XulException e ) {
      fail( "XulException: " + e.getMessage() );
    }
  }

  @Test
  public final void testStart() {
    try {
      runner.initialize();
      runner.start();
    } catch ( XulException e ) {
      fail( "XulException: " + e.getMessage() );
    }
  }

  @Test
  public final void testStop() {
    try {
      runner.initialize();
      runner.start();
      runner.stop();
    } catch ( XulException e ) {
      fail( "XulException: " + e.getMessage() );
    }
  }

  @Test
  public final void testNewLoaderInstance() {
    try {
      XulDomContainer cont = runner.getXulDomContainers().get( 0 );
      assertNotNull( cont.getXulLoader().getNewInstance() );
    } catch ( XulException e ) {
      fail( "XulException: " + e.getMessage() );
    }
  }

}
