package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

/**
 * An interface for the XUL container groupbox. A groupbox
 * behaves much like the XulBox, except that a groupbox has a border 
 * drawn around its edge, and optionally can display a title at the top, 
 * using a XulCaption widget.
 * 
 *  Unlike a XulBox, the groupbox defaults it orientation to vertical instead of 
 *  horizontal. 
 *  
 * @author nbaker
 *
 */
public interface XulGroupbox extends XulContainer{
  
  /**
   * Set the title on the top of the groupbox (optional).
   * 
   * @param caption The groupbox's title text. 
   */
  public void setCaption(String caption);
}
