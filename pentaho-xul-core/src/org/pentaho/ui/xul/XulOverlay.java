package org.pentaho.ui.xul;

public interface XulOverlay {
  
  /**
   * Returns the id of this overlay. The id is determined by the provider of the 
   * overlay. 
   * @return Overlay id
   */
  public String getId();
  
  /**
   * Returns a URI to the resource bundle for this overlay. The URI can be
   * a file or a HTTP URI.
   * @return Resource bundle URI
   */
  public String getResourceBundleUri();
  
  /**
   * Returns the XML source for the overlay. The overlay object should implement this
   * method or the getOverlayUri method.
   * @return Overlay XML
   */
  public String getSource();
  
  
  /**
   * Returns the URI for the overlay file. The overlay object should implement this
   * method or the getOverlayUri method.The URI can be a file or a HTTP URI.
   * @return Overlay URI
   */
  public String getOverlayUri();

}
