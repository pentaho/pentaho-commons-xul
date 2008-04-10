/**
 * 
 */
package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

/**
 * @author OEM
 *
 */
public interface XulMenu extends XulContainer{
  public String getAcceltext();
  public void setAcceltext(String accel);
  
  public String getLabel();
  public void setLabel(String label);
  
  public String getAccesskey();
  public void setAccesskey(String accessKey);

  public boolean getDisabled();
  public void setDisabled(boolean disabled);
  
  
}
