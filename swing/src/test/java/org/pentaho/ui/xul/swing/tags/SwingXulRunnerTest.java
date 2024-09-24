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
