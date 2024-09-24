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

package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

import javax.swing.ImageIcon;
import java.awt.Image;

public class ImageHandler extends AbstractXulEventHandler {
  public void switchSrc() {
    XulImage img = (XulImage) document.getElementById( "img" );
    Image newImg =
        new ImageIcon( ImageHandler.class.getClassLoader().getResource( "resource/documents/testImage2.png" ) )
            .getImage();
    img.setSrc( newImg );
  }
}
