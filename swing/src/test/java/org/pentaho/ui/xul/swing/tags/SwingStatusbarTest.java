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
