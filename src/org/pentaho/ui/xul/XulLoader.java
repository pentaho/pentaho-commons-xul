/**
 * 
 */
package org.pentaho.ui.xul;

import org.dom4j.Document;

/**
 * The XulLoader will build the DOM model and corresponding managed UI implementation
 * from the XUL document. 
 *  
 * @author nbaker
 *
 */
public interface XulLoader {
  
  /**
   * Loads a XUL document into a container, building the necessary model, impl and event handling.
   *  
   * @param xulDocument An XML document initialized from XUL XML. 
   * @return The container holding the modeled UI.
   * @throws IllegalArgumentException Exception thrown if any problems are encountered instantiating
   * the XUL library component from the XUL XML.  
   * @throws XulException Exception thrown if errors encountered getting an instance of the parser. 
   */
  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException;
  
  /**
   * Loads a XUL document fragment into a container, building the necessary model, impl and event handling.
   * 
   * @param xulDocument An XML document initialized from XUL XML. 
   * @return The container holding the modeled fragment.
   * @throws IllegalArgumentException Exception thrown if any problems are encountered instantiating
   * the XUL library component from the XUL XML.  
   * @throws XulException Exception thrown if errors encountered getting an instance of the parser. 
   */
  public XulFragmentContainer loadXulFragment(Document xulDocument) throws IllegalArgumentException, XulException;
}
