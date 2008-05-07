/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.Map;
import java.util.ResourceBundle;

import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.XulEventHandler;

/**
 * @author OEM
 *
 */
public interface XulDomContainer {
  
  public void addDocument(Document document);
  
  public Document getDocumentRoot();
  
  public Document getDocument(int idx);
  
  public XulDomContainer loadFragment(String xulLocation) throws XulException;
  
  public void addEventHandler(String id, String eventClassName) throws XulException;

  public XulDomContainer loadFragment(String xulLocation, ResourceBundle res) throws XulException;
  
  public XulEventHandler getEventHandler(String key) throws XulException;
  
  @Deprecated
  public void setEventHandler(String key, XulEventHandler handler);
  
  /**
   * Registers an event handler with elements of this container.  Attributes of command-type elements
   * can refer to an event handler by name.  See {@link XulEventHandler#getName()}
   * @param handler - a XulEventHandler
   */
  public void setEventHandler(XulEventHandler handler);
  
  public XulMessageBox createMessageBox(String message);

  public XulMessageBox createErrorMessageBox(String title, String message, Throwable throwable);

  public void initialize();
  
  public void close();
  
  public boolean isClosed();
  
  public XulLoader getXulLoader();
  
  public void mergeContainer(XulDomContainer container);
  
  public Map<String, XulEventHandler> getEventHandlers();
  

  /**
   * Execute the method passed, with any args
   * as parameters. This invokation is used for 
   * plumbing event handlers to the event methods. 
   * 
   * @param method The method to execute
   * @param args Any parameters needed for the method. 
   */
  public void invoke(String method, Object[] args) throws XulException;
  
}
