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
 * Copyright (c) 2020 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags;

import org.dom4j.Document;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

import java.awt.*;

import static org.junit.Assert.*;

public class SwtGroupboxTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;

  @Before
  public void setUp() throws Exception {
    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/groupboxtest.xul" );

    runner = new SwtXulRunner();
    runner.addContainer( container );
  }

  @Test
  public void testSetCaption() throws Exception {

    SwtGroupbox groupBox = (SwtGroupbox) container.getDocumentRoot().getElementById( "database-options-box" );
    String expectedCaptionText = "someText";
    groupBox.setCaption( expectedCaptionText );
    assertEquals(expectedCaptionText, groupBox.label.getValue());
  }

  @Test
  public void testSetCaptionReadCaptionText() throws Exception {

    SwtGroupbox groupBox = (SwtGroupbox) container.getDocumentRoot().getElementById( "database-options-box" );
    String expectedCaptionText = "Settings";
    assertEquals(expectedCaptionText, groupBox.label.getValue());
  }

  @Test(expected = Test.None.class /* no exception expected */)
  public void testSetCaptionNullLabel() throws Exception {

    SwtGroupbox groupBox = (SwtGroupbox) container.getDocumentRoot().getElementById( "database-options-box" );
    groupBox.label = null;
    groupBox.setCaption( "someText" );

    // test there is no null exception thrown
  }
}
