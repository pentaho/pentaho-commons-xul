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
public class SwingVboxHandler implements XulTagHandler{
  
  public SwingVbox parse(Element element, XulContainer parent, XulRunner xulRunner) {
    //TODO: break out to factory
    SwingVbox tag  = new SwingVbox();
    return tag;
    
  }
}