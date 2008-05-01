/**
 * 
 */
package org.pentaho.ui.xul;

import org.pentaho.ui.xul.dom.Element;

/**
 * The base interface for any XUL widget. 
 * 
 * @author nbaker
 *
 */
public interface XulComponent extends Element {
  
  /**
   * The manageObject is the rendering control or container that 
   * corresponds to this XUL component.
   * @return the impl control that represents this XUL component under the covers. 
   */
  public Object getManagedObject();
  
  /**
   * The name is the tag name that this component corresponds to 
   * in XUL XML.
   * 
   * @return the XUL tag name. 
   */
  public String getName();
  
  /**
   * Every element in XUL must have a unique id
   * @param id sets the component's id
   */
  public void setId(String id);
  
  /**
   * 
   * @return the id for this component. 
   */
  public String getId();
  
  /**
   * From the XUL specification: http://www.xulplanet.com/references/elemref/ref_XULElement.html#attr_flex
   * Indicates the flexibility of the element, which indicates how an element's 
   * container distributes remaining empty space among its children. Flexible elements 
   * grow and shrink to fit their given space. Elements with larger flex values will be 
   * made larger than elements with lower flex values, at the ratio determined by the two 
   * elements. The actual value is not relevant unless there are other flexible elements 
   * within the same container. Once the default sizes of elements in a box are calculated, 
   * the remaining space in the box is divided among the flexible elements, according to 
   * their flex ratios.   
   * 
   * @return the flex value for this component
   */
  public int getFlex();
  
  /**
   * This field makes sense only relative to the values of its siblings. NOTE that 
   * if only one sibling has a flex value, then that sibling gets ALL the 
   * extra space in the container, no matter what the flex value is. 
   * 
   * @param flex
   */
  public void setFlex(int flex);
  
  /**
   * Set the width of this control
   * 
   */
  public void setWidth(int width);
  
  /**
   * Returns the width of the component
   * @return the component's width
   */
  public int getWidth();
  
  /**
   * Set the height of the component
   * 
   */
  public void setHeight(int height);
  

  /**
   * Returns the height of the component
   * @return the component's height
   */
  public int getHeight();
  
}
