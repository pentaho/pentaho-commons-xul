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

package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertEquals;

import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.ImageIcon;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.samples.ImageHandler;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.tags.SwingImage;

public class SwingImageTest {
  Document doc = null;
  XulDomContainer container;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/images.xul" );

    doc = container.getDocumentRoot();
  }

  @Test
  public void testImage() throws Exception {
    SwingImage img = (SwingImage) doc.getElementById( "img_dimed" );
    assertEquals( "testImage.png", img.getSrc() );
    assertEquals( 200, img.getHeight() );
    assertEquals( 200, img.getWidth() );
    img.refresh();
  }

  @Test
  public void testSetSrc() throws Exception {
    SwingImage img = (SwingImage) doc.getElementById( "img_dimed" );
    assertEquals( "testImage.png", img.getSrc() );

    Image newImg =
        new ImageIcon( ImageHandler.class.getClassLoader().getResource( "documents/testImage2.png" ) )
            .getImage();
    img.setSrc( newImg );
  }

  @Test
  public void testSetSrcBad() throws Exception {
    SwingImage img = (SwingImage) doc.getElementById( "img_dimed" );
    assertEquals( "testImage.png", img.getSrc() );
    img.setSrc( "foo.bar.jpg" );
    assertEquals( "testImage.png", img.getSrc() );
  }
}
