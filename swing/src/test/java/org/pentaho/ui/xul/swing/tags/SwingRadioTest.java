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


package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulRadio;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingRadioTest {

  XulRunner runner = null;

  Document doc = null;

  XulDomContainer container;

  XulRadio radio1;

  XulRadio radio2;
  XulRadio radio3;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/radioTest.xul" );

    runner = new SwingXulRunner();
    runner.addContainer( container );
    radio1 = (XulRadio) container.getDocumentRoot().getElementById( "radio1" );
    radio2 = (XulRadio) container.getDocumentRoot().getElementById( "radio2" );
    radio3 = (XulRadio) container.getDocumentRoot().getElementById( "radio3" );

  }

  @After
  public void tearDown() throws Exception {
    if ( runner != null ) {
      runner.stop();
    }
  }

  @Test
  public void testIsSelected() throws Exception {
    // test initial value from parse
    assertTrue( radio2.isSelected() );
    assertTrue( !radio1.isSelected() );

  }

  @Test
  public void testSetSelected() throws Exception {
    radio1.setSelected( true );
    assertTrue( !radio2.isSelected() );
    assertTrue( radio1.isSelected() );
  }

  @Test
  public void testGetLabel() throws Exception {
    // test initial value from parse
    assertEquals( "Test 1", radio1.getLabel() );
  }

  @Test
  public void testSetLabel() throws Exception {
    radio1.setLabel( "testing" );
    assertEquals( "testing", radio1.getLabel() );
  }

  @Test
  public void testIsDisabled() throws Exception {
    // test initial value from parse
    assertTrue( radio1.isDisabled() == false );
    assertTrue( radio3.isDisabled() );
  }

  @Test
  public void testSetDisabled() throws Exception {
    // test initial value from parse
    radio1.setDisabled( true );
    assertTrue( radio1.isDisabled() );
  }
}
