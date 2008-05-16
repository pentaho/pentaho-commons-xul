/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.Element;

/**
 * @author nbaker
 *
 */
public class SwingListitem extends SwingElement implements XulListitem{
  
  private String label;
  private Object value;
  
  public SwingListitem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("listitem");
  }
  
  public String getLabel(){
    return label;
  }
  
  public void setLabel(String label){
    this.label = label;
    managedObject = this.label;
  }
  
  public String toString(){
    return this.label;
  }
  
  public void layout(){
  }

  public Object getValue() {
    return value;
  }

  public boolean isSelected() {
    return false;
  }

  public void setSelected(boolean selected) {
  }

  public void setValue(Object value) {
    this.value = value;
  }
  
}