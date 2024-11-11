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
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingButtonTest {

  static XulRunner runner = null;
  static Document doc = null;
  static XulDomContainer container;

  @BeforeClass
  public static void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/imageButton.xul" );

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
  public final void testButtonClick() {
    XulButton button = (XulButton) container.getDocumentRoot().getElementById( "plainButton" );
    button.doClick();
  }

  @Test
  public void testButtonGroup() {
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById( "firstButton" );
    XulButton button2 = (XulButton) container.getDocumentRoot().getElementById( "secondButton" );
    button2.doClick();

    assertTrue( button2.isSelected() );
  }

  // @Test
  // public void testSetSelected(){
  // XulButton button1 = (XulButton) container.getDocumentRoot().getElementById("firstButton");
  //
  // button1.setSelected("true");
  // assertTrue(button1.isSelected());
  // button1.setSelected("false");
  // assertTrue(!button1.isSelected());
  //
  // button1.setSelected(true);
  // assertTrue(button1.isSelected());
  // button1.setSelected(false);
  // assertTrue(!button1.isSelected());
  //
  // }

  @Test
  public void testGetImage() {
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById( "firstButton" );
    assertEquals( "testImage.png", button1.getImage() );
  }

  @Test
  public void testGetLabel() {
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById( "firstButton" );
    assertEquals( "TOP", button1.getLabel() );
  }

  @Test
  public void testGetDir() {
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById( "firstButton" );
    assertEquals( "reverse", button1.getDir() );
  }
}
