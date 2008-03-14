/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulTagHandler;
import org.pentaho.ui.xul.XulWindowContainer;
import org.dom4j.Element;

/**
 * @author OEM
 *
 */
public class SwingGroupboxHandler implements XulTagHandler{
  
  public SwingGroupbox parse(Element element, XulContainer parent, XulWindowContainer xulWindowContainer) {
    //TODO: break out to factory
    SwingGroupbox tag  = new SwingGroupbox();
    return tag;
    
  }
}
