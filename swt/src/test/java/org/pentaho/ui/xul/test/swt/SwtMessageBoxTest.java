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
import static org.junit.Assert.assertFalse;

import java.awt.GraphicsEnvironment;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;

public class SwtMessageBoxTest {

  Document doc = null;
  XulDomContainer container;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/messagetest.xul" );

    doc = container.getDocumentRoot();

  }

  @Test
  public void showMessageTest() throws Exception {
    final XulMessageBox msg = (XulMessageBox) doc.createElement( "messagebox" );
    msg.setTitle( "Title" );
    msg.setMessage( "Message" );

    assertEquals( "Title", msg.getTitle() );
    assertEquals( "Message", msg.getMessage() );

  }

  @Test
  public void showMessageButtonTest() throws Exception {
    final XulMessageBox msg = (XulMessageBox) doc.createElement( "messagebox" );

    Object[] btns = new String[] { "foo", "bar" };

    msg.setButtons( btns );

    assertEquals( btns, msg.getButtons() );

  }

  boolean fail = false;

  @Test
  public void testGetParent() throws Exception {

    final XulMessageBox msg = (XulMessageBox) doc.createElement( "messagebox" );
    Thread thrd = new Thread() {
      public void run() {
        msg.setMessage( "Bang" );
        msg.open();
        fail = true;
      }
    };
    thrd.start();
    assertFalse( fail );

  }
}
