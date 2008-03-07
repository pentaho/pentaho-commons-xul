/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulTagHandler;

/**
 * @author OEM
 *
 */
public class SwingWindowHandler implements XulTagHandler{
  
  public SwingWindow parse(Element element, XulContainer parent, XulRunner xulRunner) {
    //TODO: break out to factory
    String title = element.attributeValue("title");
    String className = element.attributeValue("eventHandler");
    int height = Integer.valueOf(element.attributeValue("height"));
    int width = Integer.valueOf(element.attributeValue("width"));
    
    SwingWindow tag  = new SwingWindow(title);
    tag.setXulRunner(xulRunner);
    tag.setWidth(width);
    tag.setHeight(height);
    tag.setEventHandlerClass(className);
    return tag;
    
  }
}
