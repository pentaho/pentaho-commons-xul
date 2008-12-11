/**
 * 
 */
package org.pentaho.ui.xul;

import org.pentaho.ui.xul.util.Align;
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
   * The method used to remove a child from a container
   * @param component the child component to remove from this container.
   */
  public void removeComponent(XulComponent component);
  
  /**
   * @see org.pentaho.ui.xul.util.Orient
   * @return the orientation for this container. Valid values are found in the Orient enum. 
   */
  public Orient getOrientation();
  

  /**
   * Specifies the alignment of children when the size of the container is greater than the
   * size of it's children.
   * 
   * @param align one of [start, center, end].
   */
  public void setAlign(String align);
  
  /**
   * Returns the alignment of children.
   * 
   * @return String specifying the alignment [start, center, end]. 
   */
  public Align getAlign();
}
