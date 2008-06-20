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
public interface XulContainer extends XulComponent {
  
  /**
   * The method used to add child controls\widgets to a container.
   * @param component the child component to add to this container.
   */
  public void addComponent(XulComponent component);
  
  /**
   * The method used to add child controls\widgets to a container at the specified index
   * @param component the child component to add to this container.
   * @param index to add the component at (zero-based)
   */
  public void addComponentAt(XulComponent component, int idx);
  
  /**
   * @see org.pentaho.ui.xul.util.Orient
   * @return the orientation for this container. Valid values are found in the Orient enum. 
   */
  public Orient getOrientation();
}
