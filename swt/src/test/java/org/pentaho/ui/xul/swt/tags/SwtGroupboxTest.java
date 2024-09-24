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
