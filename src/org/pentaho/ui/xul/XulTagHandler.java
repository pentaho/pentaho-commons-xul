/**
 * 
 */
package org.pentaho.ui.xul;

import org.dom4j.Element;


/**
 * @author OEM
 *
 */
public interface XulTagHandler {
  public XulElement parse(Element element, XulContainer parent, XulWindowContainer xulWindowContainer);
  
}
