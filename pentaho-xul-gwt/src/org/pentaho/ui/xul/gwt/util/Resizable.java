package org.pentaho.ui.xul.gwt.util;

/**
 * Tags should implement this interface if they contain elements that need to be notified of resize
 * and visibility changes.
 * 
 * @author nbaker
 *
 */
public interface Resizable {
  
  /**
   * Called when a parent has resized or changed visibility.
   */
  public void onResize();
}
