/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulTagHandler;

/**
 * @author OEM
 *
 */
public class SwingLabelHandler implements XulTagHandler{
  
  public SwingLabel parse(Element element, XulContainer parent, XulRunner xulRunner) {
    //TODO: break out to factory
    String text = element.attributeValue("value");
    return new SwingLabel(text);

  }
}