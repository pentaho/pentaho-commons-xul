/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.dom4j.Element;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulTagHandler;

/**
 * @author OEM
 *
 */
public class SwingWindowHandler implements XulTagHandler{
  
  public SwingWindow parse(Element element, XulContainer parent, XulWindowContainer xulWindowContainer) {
    //TODO: break out to factory
    String title = element.attributeValue("title");
    int height = Integer.valueOf(element.attributeValue("height"));
    int width = Integer.valueOf(element.attributeValue("width"));
    
    SwingWindow tag  = new SwingWindow(title);
    tag.setXulWindowContainer(xulWindowContainer);
    tag.setWidth(width);
    tag.setHeight(height);
    
    return tag;
    
  }
}
