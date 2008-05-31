/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.Map;
import java.util.ResourceBundle;

import org.pentaho.ui.xul.binding.Binding;
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
  
  /**
   * @deprecated
   * We are getting away from using xul-instantiated event handlers.  It is prefered that the 
   * application set the event handler via {@link XulDomContainer#addEventHandler(XulEventHandler)}
   */
  public void addEventHandler(String id, String eventClassName) throws XulException;

  public XulDomContainer loadFragment(String xulLocation, ResourceBundle res) throws XulException;
  
  public XulEventHandler getEventHandler(String key) throws XulException;
  
  /**
   * Registers an event handler with elements of this container.  Attributes of command-type elements
   * within a xul file can refer to an event handler by name.  See {@link XulEventHandler#getName()}.
   * Adding an event handler by a name that is already registered will completely mask out and replace
   * the original event handler.
   * @param handler - a XulEventHandler
   */
  public void addEventHandler(XulEventHandler handler);
  
  public void addBinding(Binding binding);

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
  
  public void registerBinding(XulComponent comp, String expr);
  
  public void createBinding(XulEventSource source, String sourceAttr, String targetId, String targetAttr);
  public void createBinding(String source, String sourceAttr, String targetId, String targetAttr);
}
