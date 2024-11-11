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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.GraphicsEnvironment;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulStatusbarpanel;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulStatusbar;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingStatusbarTest {
  Document document = null;
  XulDomContainer container;
  XulMenu menu;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/statusbar.xul" );

    document = container.getDocumentRoot();

  }

  @Test
  public void testStatusbarThere() throws Exception {

    XulStatusbar bar = (XulStatusbar) document.getElementById( "bar" );
    XulStatusbarpanel panel1 = (XulStatusbarpanel) document.getElementById( "imagePanel" );
    XulStatusbarpanel panel2 = (XulStatusbarpanel) document.getElementById( "rightPanel" );

    assertNotNull( bar );
    assertNotNull( panel1 );
    assertNotNull( panel2 );

  }

  @Test
  public void testImage() throws Exception {

    XulStatusbarpanel panel1 = (XulStatusbarpanel) document.getElementById( "imagePanel" );

    assertEquals( "testImage.png", panel1.getImage() );
  }

  @Test
  public void testLabel() throws Exception {

    XulStatusbarpanel panel1 = (XulStatusbarpanel) document.getElementById( "rightPanel" );

    assertEquals( "Right panel", panel1.getLabel() );
  }

}
