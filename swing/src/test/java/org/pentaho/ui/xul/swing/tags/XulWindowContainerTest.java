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
