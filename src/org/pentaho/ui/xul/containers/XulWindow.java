/**
 * 
 */
package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulWindowContainer;

/**
 * @author OEM
 *
 */
public interface XulWindow extends XulContainer{
  public void setTitle(String title);

  public int getWidth();
  public int getHeight();
  public void setXulDomContainer(XulDomContainer xulDomContainer);
  public XulDomContainer getXulDomContainer();
  
  public void invoke(String method, Object[] args);
  
  
}
