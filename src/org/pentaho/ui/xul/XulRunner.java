/**
 * 
 */
package org.pentaho.ui.xul;

import org.dom4j.Document;



/**
 * @author OEM
 *
 */
public interface XulRunner {
  
  public void initialize() throws XulException;
  public void start() throws XulException;
  public void stop() throws XulException;
  
  public Document getDocumentRoot();

  public void setDocumentRoot(Document document);
  public Document remoteCall(XulServiceCall serviceUrl);
  
  public XulComponent getElementById(String id);
  public XulComponent getElementsById(String id);
  
}
