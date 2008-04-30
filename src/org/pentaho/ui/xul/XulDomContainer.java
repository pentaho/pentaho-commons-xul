/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.List;
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
  
  public XulDomContainer loadFragment(String xulLocation) throws XulException;
  
  public void addEventHandler(String id, String eventClassName) throws XulException;

  public XulDomContainer loadFragment(String xulLocation, ResourceBundle res) throws XulException;
  
  public XulEventHandler getEventHandler(String key) throws XulException;
  
  public XulMessageBox createMessageBox(String message);

  public XulMessageBox createErrorMessageBox(String title, String message, Throwable throwable);

  public void initialize();
  
  public void close();
  
  public boolean isClosed();
  
  public XulLoader getXulLoader();
  
  public void mergeContainer(XulDomContainer container);
  
  public Map<String, XulEventHandler> getEventHandlers();
  
}
