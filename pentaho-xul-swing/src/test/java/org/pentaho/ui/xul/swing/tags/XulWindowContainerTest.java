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
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.awt.GraphicsEnvironment;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.impl.XulWindowContainer;
import org.pentaho.ui.xul.samples.CloseHandler;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class XulWindowContainerTest {
  static XulDomContainer container;
  static XulRunner runner;

  @BeforeClass
  public static void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/windowContainerTest.xul" );
    runner = new SwingXulRunner();
    runner.addContainer( container );
  }

  @AfterClass
  public static void tearDown() throws Exception {
    if ( runner != null ) {
      runner.stop();
    }
  }

  @Test
  public final void testFrag() throws Exception {
    XulDomContainer container2 = container.loadFragment( "documents/fragmenttest.xul" );
    assertNotNull( container2.getDocumentRoot().getElementById( "database-options-box" ) );
  }

  @Test
  public final void testFrag2() throws Exception {
    XulDomContainer container2 = container.loadFragment( "documents/fragmenttest.xul" );
    assertNotNull( container2.getDocumentRoot().getElementById( "database-options-box" ) );
  }


  private boolean fail = false;
  @Test
  public final void testClose() throws Exception {
    final String lock = "1";
    Thread t = new Thread( () -> {
      try {
        synchronized ( lock ) {
          XulDomContainer container = new SwingXulLoader().loadXul( "documents/windowContainerTest.xul" );
          XulRunner runner = new SwingXulRunner();
          runner.addContainer( container );
          runner.initialize();
          runner.start();

          CloseHandler handler = (CloseHandler) container.getEventHandler( "closeHandler" );
          ( (XulWindowContainer) container ).ignoreCloseOperation( true );
          container.close();
          fail |= !handler.closed1;
          fail |= !handler.closed2;
          fail |= !handler.closed3;
        }

      } catch ( Exception e ) {
        fail = true;
      }
    } );

    t.start();
    t.join();
    assertFalse( fail );

  }

}
