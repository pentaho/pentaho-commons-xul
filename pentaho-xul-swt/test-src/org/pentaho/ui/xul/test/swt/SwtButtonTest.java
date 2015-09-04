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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtButton;
import org.pentaho.ui.xul.swt.tags.SwtLabel;

public class SwtButtonTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "resource/documents/imageButton.xul" );

    runner = new SwtXulRunner();
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

  // @TODO implement button group test
  // @Test
  // public void testButtonGroup(){
  // XulButton button1 = (XulButton) container.getDocumentRoot().getElementById("firstButton");
  // XulButton button2 = (XulButton) container.getDocumentRoot().getElementById("secondButton");
  // button2.doClick();
  //
  // assertTrue(button2.isSelected());
  // }
  //
  @Test
  public void testSetSelected() {
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById( "firstButton" );

    button1.setSelected( "true" );
    assertTrue( button1.isSelected() );
    button1.setSelected( "false" );
    assertTrue( !button1.isSelected() );

    button1.setSelected( true );
    assertTrue( button1.isSelected() );
    button1.setSelected( false );
    assertTrue( !button1.isSelected() );

  }

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

  @Test
  public void testImageButton() {
    XulButton button = (XulButton) container.getDocumentRoot().getElementById( "firstButton" );
    assertTrue( "Button with label and image set should render SwtButton", button.getManagedObject() instanceof SwtButton );
    button = (XulButton) container.getDocumentRoot().getElementById( "imageButton" );
    assertTrue( "Button without label and with image set should render SwtLabel", button.getManagedObject() instanceof SwtLabel );
  }
}
