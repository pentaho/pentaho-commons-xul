/**
 * 
 */
package org.pentaho.ui.xul.dom;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;


/**
 * @author NBaker
 *
 */
public interface Document extends Element{
  public XulComponent getRootElement();
  public XulComponent createElement(String elementName) throws XulException;
  public boolean isRegistered(String elementName);
  public void setXulDomContainer(XulDomContainer container);
  public void addOverlay(String src) throws XulException;
  public void removeOverlay(String src) throws XulException;
  
}
