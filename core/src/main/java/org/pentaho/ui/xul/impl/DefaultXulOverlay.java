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


package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulOverlay;
import org.pentaho.ui.xul.XulOverlayFragment;

import java.util.List;

public class DefaultXulOverlay implements XulOverlay {

  private String id;
  private String overlayUri;
  private String source;
  private String resourceBundleUri;
  private int priority;
  private List<XulOverlayFragment> fragments;

  public DefaultXulOverlay( String id, String overlayUri, String source, String resourceBundleUri ) {
    this( id, overlayUri, source, resourceBundleUri, DEFAULT_PRIORITY );
  }

  public DefaultXulOverlay( String id, String overlayUri, String source, String resourceBundleUri, int priority ) {
    this.id = id;
    this.overlayUri = overlayUri;
    this.source = source;
    this.resourceBundleUri = resourceBundleUri;
    this.priority = priority;
  }

  public DefaultXulOverlay( String overlayUri ) {
    this.overlayUri = overlayUri;
  }

  public String getId() {
    return id;
  }

  public String getOverlayUri() {
    return overlayUri;
  }

  public String getOverlayXml() {
    return getSource();
  }

  public String getResourceBundleUri() {
    return resourceBundleUri;
  }

  public String getSource() {
    return source;
  }

  public List<XulOverlayFragment> getOverlayFragments() {
    return fragments;
  }

  public int getPriority() {
    return priority;
  }

}
