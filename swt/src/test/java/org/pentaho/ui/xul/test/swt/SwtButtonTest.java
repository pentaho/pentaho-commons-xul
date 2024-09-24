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
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtButton;

public class SwtButtonTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/imageButton.xul" );

    runner = new SwtXulRunner();
    runner.addContainer( container );
  }

  @Test
  public final void testButtonClick() {
    XulButton button = (XulButton) container.getDocumentRoot().getElementById( "plainButton" );
    button.doClick();
  }

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
    assertTrue( "SwtButton", button instanceof SwtButton );
    assertTrue( "Button with label and image set should render Swt Button",
      button.getManagedObject() instanceof Button );
    button = (XulButton) container.getDocumentRoot().getElementById( "imageButton" );
    assertTrue( "Button without label and with image set should render Swt Label",
      button.getManagedObject() instanceof Label );
  }
}
