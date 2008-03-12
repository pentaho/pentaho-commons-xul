/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.util.List;

import org.dom4j.Element;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulTagHandler;
import org.pentaho.ui.xul.XulWindowContainer;

/**
 * @author OEM
 *
 */
public class SwingCheckboxHandler implements XulTagHandler{
  
  public SwingCheckbox parse(Element element, XulContainer parent, XulWindowContainer xulWindowContainer) {
    //TODO: break out to factory
    String label = element.attributeValue("label");
    
    List attributes = element.attributes();
    
    SwingCheckbox checkBox = new SwingCheckbox(label);
    checkBox.setAttributes(attributes);
    checkBox.setAttributeValue("ID", element.attributeValue("id"));
    return checkBox;

  }
}