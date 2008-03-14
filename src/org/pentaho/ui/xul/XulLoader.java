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
  public XulRunner loadXul(Document xulDocument) throws IllegalArgumentException, XulException;
}
