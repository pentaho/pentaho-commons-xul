/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulTagHandler;
import org.pentaho.ui.xul.containers.XulVbox;;

/**
 * @author OEM
 *
 */
public class SwingHboxHandler implements XulTagHandler{
  
  public SwingHbox parse(Element element, XulContainer parent, XulRunner xulRunner) {
    //TODO: break out to factory
    SwingHbox tag  = new SwingHbox();
    return tag;
    
  }
}