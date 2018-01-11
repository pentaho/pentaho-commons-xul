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
