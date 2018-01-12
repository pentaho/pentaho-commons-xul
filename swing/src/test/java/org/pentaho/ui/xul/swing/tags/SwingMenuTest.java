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
