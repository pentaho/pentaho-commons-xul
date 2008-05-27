/**
 * 
 */
package org.pentaho.ui.xul;

import java.beans.PropertyChangeListener;

import org.pentaho.ui.xul.dom.Element;

/**
 * The base interface for any XUL widget. 
 * 
 * @author nbaker
 *
 */
public interface XulComponent extends Element, XulEventSource {
  
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
   * Sets the method that will be invoked when this component
   * loses focus. Also hooks up any listeners for this event. 
   * @param method the method to execute when the focus is lost. 
   */
  public void setOnblur(String method);
  
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
  

  public void addPropertyChangeListener(PropertyChangeListener listener);
  public void removePropertyChangeListener(PropertyChangeListener listener);
  
  /**
   * Sets the enablement state of the component
   * @param disabled sets this components enabled state
   * 
   */
  public void setDisabled(boolean disabled);
  
  /**
   * XUL's attribute is "disabled", thus this acts
   * exactly the opposite of SWT/Swing/AWT. If the property is not 
   * available, then the control is enabled. 
   *
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled();
  
}
