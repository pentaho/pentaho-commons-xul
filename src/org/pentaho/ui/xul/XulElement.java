/**
 * 
 */
package org.pentaho.ui.xul;

import org.dom4j.QName;
import org.dom4j.tree.BaseElement;



/**
 * @author OEM
 *
 */
public class XulElement extends BaseElement implements XulComponent {
  protected Object managedObject;
  
  public XulElement(String tagName){
    super(new QName(tagName.toLowerCase()));
  }
  
  public XulElement(String tagName, Object managedObject){
    super(new QName(tagName));
    this.managedObject = managedObject;
  }
  
  public Object getManagedObject(){
    return managedObject;
  }
}
