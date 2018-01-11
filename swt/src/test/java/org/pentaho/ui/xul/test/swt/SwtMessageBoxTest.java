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
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

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
