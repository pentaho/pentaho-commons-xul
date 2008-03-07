/**
 * 
 */
package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulRunner;

/**
 * @author OEM
 *
 */
public interface XulWindow extends XulContainer{
  public void setTitle(String title);
  public XulEventHandler getEventHandler();
  public void setEventHandlerClass(String name);
  public int getWidth();
  public int getHeight();
  public void setXulRunner(XulRunner xulRunner);
  
  public void invoke(String method, Object[] args);
}
