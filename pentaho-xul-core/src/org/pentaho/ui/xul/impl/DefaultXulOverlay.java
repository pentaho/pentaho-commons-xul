package org.pentaho.ui.xul.impl;

import java.util.List;

import org.pentaho.ui.xul.XulOverlay;
import org.pentaho.ui.xul.XulOverlayFragment;

public class DefaultXulOverlay implements XulOverlay {
  
  private String id;
  private String overlayUri;
  private String source;
  private String resourceBundleUri;
  private List<XulOverlayFragment> fragments;
  
  public DefaultXulOverlay( String id, String overlayUri, String source, String resourceBundleUri ) {
    this.id = id;
    this.overlayUri = overlayUri;
    this.source = source;
    this.resourceBundleUri = resourceBundleUri;
  }
  
  public DefaultXulOverlay(String overlayUri){
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
  
  
}
