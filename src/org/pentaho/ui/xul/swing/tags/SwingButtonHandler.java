/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.dom4j.Element;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulTagHandler;
import org.pentaho.ui.xul.XulWindowContainer;


/**
 * @author OEM
 *
 */
public class SwingButtonHandler implements XulTagHandler{
  
  public SwingButton parse(Element element, XulContainer parent, XulWindowContainer xulWindowContainer) {
    //TODO: break out to factory
    String text = element.attributeValue("label");
    SwingButton button = new SwingButton(text);
    
    String funct = element.attributeValue("onClick");
    if(funct != null){
      button.setOnClick(funct);
    }
    return button;

  }
}
