/**
 * 
 */
package org.pentaho.ui.xul;

/**
 * @author nbaker
 *
 */
public interface XulComponent {
  
  /**
   * 
   * @return
   */
  public Object getManagedObject();
  
  /**
   * 
   * @return
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
}
