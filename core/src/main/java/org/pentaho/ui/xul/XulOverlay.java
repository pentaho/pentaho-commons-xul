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

package org.pentaho.ui.xul;

public interface XulOverlay {

  /**
   * Default priority value assigned to an overlay when the priority attribute is not present in the overlay XML.
   * Priority value is used to determine the order in which to apply overlays in order to guarantee the order of
   * elements supplied by overlays.
   */
  final int DEFAULT_PRIORITY = 9999;

  /**
   * Returns the id of this overlay. The id is determined by the provider of the overlay.
   * 
   * @return Overlay id
   */
  public String getId();

  /**
   * Returns a URI to the resource bundle for this overlay. The URI can be a file or a HTTP URI.
   * 
   * @return Resource bundle URI
   */
  public String getResourceBundleUri();

  /**
   * Returns the XML source for the overlay. The overlay object should implement this method or the getOverlayUri
   * method.
   * 
   * @return Overlay XML
   */
  public String getSource();

  @Deprecated
  // use getSource
  public
  String getOverlayXml();

  /**
   * Returns the URI for the overlay file. The overlay object should implement this method or the getOverlayUri
   * method.The URI can be a file or a HTTP URI.
   * 
   * @return Overlay URI
   */
  public String getOverlayUri();

  /**
   * Returns the int indicating the priority order in which the overlay should be applied
   * 
   * @return priority integer value
   */
  public int getPriority();

}
