/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.ResourceBundle;

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
  public XulDomContainer loadXulFragment(Document xulDocument) throws IllegalArgumentException, XulException;
  
  /**
   * Loads a XUL document into a container, building the necessary model, impl and event handling.
   * 
   * This method will look for a ResourceBundle Property file based on the resource name with a .properties extension.
   * If a Resource Bundle is found the Xul file is processed for any externalized strings
   * 
   * @param resource The location of the Xul document to load. 
   * @return The container holding the modeled UI.
   * @throws IllegalArgumentException Exception thrown if any problems are encountered instantiating
   * the XUL library component from the XUL XML.  
   * @throws XulException Exception thrown if errors encountered getting an instance of the parser. 
   */
  public XulDomContainer loadXul(String resource) throws IllegalArgumentException, XulException;
  
  /**
   * Loads a XUL document into a container, building the necessary model, impl and event handling.
   * 
   * Processes the Xul Document for any externalized strings contained in the Resource Bundle.
   * 
   * @param resource The location of the Xul document to load. 
   * @param bundle The Message ResourceBundle used to build the document. 
   * @return The container holding the modeled UI.
   * @throws IllegalArgumentException Exception thrown if any problems are encountered instantiating
   * the XUL library component from the XUL XML.  
   * @throws XulException Exception thrown if errors encountered getting an instance of the parser. 
   */
  public XulDomContainer loadXul(String resource, ResourceBundle bundle) throws  XulException;

  

  
  /**
   * Loads a XUL document fragment into a container, building the necessary model, impl and event handling.
   * 
   * This method will look for a ResourceBundle Property file based on the resource name with a .properties extension.
   * If a Resource Bundle is found the Xul file is processed for any externalized strings
   * 
   * @param resource The location of the Xul document to load. 
   * @return The container holding the modeled fragment.
   * @throws IllegalArgumentException Exception thrown if any problems are encountered instantiating
   * the XUL library component from the XUL XML.  
   * @throws XulException Exception thrown if errors encountered getting an instance of the parser. 
   */
  public XulDomContainer loadXulFragment(String resource) throws IllegalArgumentException, XulException;
  
  /**
   * Loads a XUL document fragment into a container, building the necessary model, impl and event handling.
   * 
   * Processes the Xul Document for any externalized strings contained in the Resource Bundle.
   * 
   * @param resource The location of the Xul document to load. 
   * @param bundle The Message ResourceBundle used to build the document. 
   * @return The container holding the modeled UI.
   * @throws IllegalArgumentException Exception thrown if any problems are encountered instantiating
   * the XUL library component from the XUL XML.  
   * @throws XulException Exception thrown if errors encountered getting an instance of the parser. 
   */
  public XulDomContainer loadXulFragment(String resource, ResourceBundle bundle) throws  XulException;

  
  /**
   * Support methed for creating XulComponents programatically at runtime (event handlers)
   * 
   * @param elementName The tag name of the element to create.
   * @return A XulComponent
   * @throws XulException Exception thrown if parser cannot handle tag
   */
  public XulComponent createElement(String elementName) throws XulException;

  
  public void register(String tagName, String className);
  
  public String getRootDir();
}
