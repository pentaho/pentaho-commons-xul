/**
 * 
 */
package org.pentaho.ui.xul.components;

/**
 * The XulCaption widget is the title that gets applied to XulGroupBox container. 
 * 
 * @author nbaker
 *
 */
public interface XulCaption {
  
  /**
   * 
   * @return the title that this caption represents. 
   */
  public String getLabel();
  
  /**
   * Applies the parameter label to the groupbox widget.
   * @param label The title to set on the groupbox. 
   */
  public void setLabel(String label);
}
