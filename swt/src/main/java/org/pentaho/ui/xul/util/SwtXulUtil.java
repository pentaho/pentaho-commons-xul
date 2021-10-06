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
 * Copyright (c) 2002-2021 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.util;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulDomContainer;

public class SwtXulUtil {

  private static Log logger = LogFactory.getLog( SwtXulUtil.class );

  public static Image getCachedImage( String src, XulDomContainer container, Display display ) {

    Image img = null;

    if ( src == null ) {
      return null;
    }

    if ( JFaceResources.getImageRegistry().getDescriptor( src ) != null ) {
      img = JFaceResources.getImageRegistry().get( src );
    }

    if ( img == null ) {
      InputStream in = null;
      try {
        in = XulUtil.loadResourceAsStream( src, container );
        if ( in != null ) {
          img = new Image( display, in );
          JFaceResources.getImageRegistry().put( src, img );
        }
      } catch ( Exception e ) {
        logger.error( e );
      } finally {
        try {
          in.close();
        } catch ( Exception ignored ) {
        }
      }
    }

    return img;
  }

  /**
   * Determines if the RUNNING_ON_WEBSPOON_MODE flag is set and returns its boolean value.
   * This is per user-basis.
   *
   * @return Boolean signalig the use of Webspoon mode.
   */
  public static boolean isRunningOnWebspoonMode() {
    return Boolean.parseBoolean( NVL( System.getenv( "RUNNING_ON_WEBSPOON_MODE" ), NVL( System.getProperty( "RUNNING_ON_WEBSPOON_MODE" ),
      "false" ) ) );
  }

  /**
   * Implements Oracle style NVL function
   *
   * @param source
   *          The source argument
   * @param def
   *          The default value in case source is null or the length of the string is 0
   * @return source if source is not null, otherwise return def
   */
  public static String NVL( String source, String def ) {
    if ( source == null || source.length() == 0 ) {
      return def;
    }
    return source;
  }
}
