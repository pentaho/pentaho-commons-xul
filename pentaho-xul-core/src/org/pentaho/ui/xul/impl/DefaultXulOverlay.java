package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulOverlay;

public class DefaultXulOverlay implements XulOverlay {
  
  private String id;
  private String overlayUri;
  private String source;
  private String resourceBundleUri;
  
  public DefaultXulOverlay( String id, String overlayUri, String source, String resourceBundleUri ) {
    this.id = id;
    this.overlayUri = overlayUri;
    this.source = source;
    this.resourceBundleUri = resourceBundleUri;
  }
  
  public String getId() {
    return id;
  }

  public String getOverlayUri() {
    return overlayUri;
  }

  public String getSource() {
    return source;
  }

  public String getResourceBundleUri() {
    return resourceBundleUri;
  }
}
