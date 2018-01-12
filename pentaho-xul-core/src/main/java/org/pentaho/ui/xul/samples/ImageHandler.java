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
