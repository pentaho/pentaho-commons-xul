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

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingMenuTest {

  Document doc = null;
  XulDomContainer container;
  XulMenu menu;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/menutest.xul" );

    doc = container.getDocumentRoot();
    menu = (XulMenu) doc.getElementById( "file-menu" );

  }

  @Test
  public void getAcceltextTest() throws Exception {
    assertEquals( "f", menu.getAcceltext() );
  }

  @Test
  public void getAccesskeyTest() throws Exception {
    assertEquals( "F", menu.getAccesskey() );
  }

  @Test
  public void testDisabled() throws Exception {
    assertTrue( menu.isDisabled() == false );
    menu.setDisabled( true );
    assertTrue( menu.isDisabled() );
  }

  @Test
  public void testMenuItemLabel() throws Exception {
    XulMenuitem item = (XulMenuitem) doc.getElementById( "paseMenuItem" );
    assertEquals( "Paste", item.getLabel() );
  }

  @Test
  public void testMenuItemGetAccesskey() throws Exception {
    XulMenuitem item = (XulMenuitem) doc.getElementById( "paseMenuItem" );
    assertEquals( "P", item.getAccesskey() );
  }

  @Test
  public void testMenuItemDisabled() throws Exception {
    XulMenuitem item = (XulMenuitem) doc.getElementById( "paseMenuItem" );
    assertTrue( item.isDisabled() );
  }

  @Test
  public void testMenuItemGetCommand() throws Exception {
    XulMenuitem item = (XulMenuitem) doc.getElementById( "paseMenuItem" );
    assertEquals( "mainController.paste()", item.getCommand() );
  }

  @Test
  public void testMenuItemGetImage() throws Exception {
    XulMenuitem item = (XulMenuitem) doc.getElementById( "paseMenuItem" );
    assertEquals( "foo.png", item.getImage() );
  }

}
