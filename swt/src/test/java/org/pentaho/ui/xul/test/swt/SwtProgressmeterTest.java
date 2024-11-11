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


package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtProgressmeterTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulProgressmeter determined;
  XulProgressmeter undetermined;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/progressmeter.xul" );

    runner = new SwtXulRunner();
    runner.addContainer( container );
    determined = (XulProgressmeter) container.getDocumentRoot().getElementById( "determined" );
    undetermined = (XulProgressmeter) container.getDocumentRoot().getElementById( "undetermined" );

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

}
