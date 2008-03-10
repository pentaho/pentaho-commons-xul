/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.dom4j.Element;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulTagHandler;
import org.pentaho.ui.xul.XulWindowContainer;

/**
 * @author OEM
 *
 */
public class SwingScriptHandler implements XulTagHandler{
  
  public SwingScript parse(Element element, XulContainer parent, XulWindowContainer xulWindowContainer) {
    //TODO: break out to factory
    String id = element.attributeValue("id");
    String className = element.attributeValue("src");
    
    xulWindowContainer.addEventHandler(id, className);
    
    return new SwingScript(id, className);

  }
}