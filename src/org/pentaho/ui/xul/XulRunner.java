/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.List;

import org.dom4j.Document;



/**
 * @author OEM
 *
 */
public interface XulRunner {
  
  public void initialize() throws XulException;
  public void start() throws XulException;
  public void stop() throws XulException;
  public void addContainer(XulWindowContainer xulWindowContainer);
  public List<XulWindowContainer> getXulWindowContainers();
  public Document remoteCall(XulServiceCall serviceUrl);
  
}
