/**
 * 
 */
package org.pentaho.ui.xul;

import org.dom4j.Document;



/**
 * @author OEM
 *
 */
public interface XulLoader {
  
  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException;
  
  public XulDomContainer loadXulFragment(Document xulDocument) throws IllegalArgumentException, XulException;
}
