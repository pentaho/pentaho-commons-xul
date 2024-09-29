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

import static org.junit.Assert.assertTrue;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtContextMenus {
  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulMenupopup popup;

  @Before
  public void setUp() throws Exception {

    container = new SwtXulLoader().loadXul( "documents/context_menu.xul" );

    runner = new SwtXulRunner();
    runner.addContainer( container );
    popup = (XulMenupopup) container.getDocumentRoot().getElementById( "contextMenu" );

    // Un-comment the following to test the GUI manually
    // runner.initialize();
    // runner.start();
  }

  @After
  public void tearDown() throws Exception {
    try {
      runner.stop();
    } catch ( Exception e ) {
    }
  }

  @Test
  public void testGetSelectedIndex() throws Exception {
    assertTrue( popup != null );
  }

}
