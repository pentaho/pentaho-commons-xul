/**
 * 
 */
package org.pentaho.ui.xul;

import org.pentaho.ui.xul.util.Orient;

/**
 * The XulContainer interface represents those controls or
 * widgets that can contain other controls or widgets. 
 * 
 * @author nbaker
 */
public interface XulContainer {
  
  /**
   * The method used to add child controls\widgets to a container.
   * @param component the child component to add to this container.
   */
  public void addComponent(XulComponent component);
  
  /**
   * @see Orient
   * @return
   */
  public Orient getOrientation();
}
