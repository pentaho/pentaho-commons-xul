/**
 * 
 */
package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;

/**
 * @author OEM
 *
 */
public interface XulWindow extends XulContainer{
  
  public static final int EVENT_ON_LOAD = 555;

  public void setTitle(String title);

  public int getWidth();
  public int getHeight();
  public void setXulDomContainer(XulDomContainer xulDomContainer);
  public XulDomContainer getXulDomContainer();
  
  public void invoke(String method, Object[] args);
  public void setOnload(String onload);
  public String getOnload();
  public void open();
  public void close();
  
  
}
