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
public abstract class XulElement extends BaseElement implements XulComponent {
  private static final long serialVersionUID = -3629792827245143824L;

  protected Object managedObject;
  
  private String id;
  
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.setAttributeValue("ID", id);
    this.id = id;
  }
  
  public abstract void layout();
}
