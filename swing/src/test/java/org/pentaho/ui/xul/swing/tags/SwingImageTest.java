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
