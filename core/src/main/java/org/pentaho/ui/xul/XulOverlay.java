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
