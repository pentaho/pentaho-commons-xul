/**
 * 
 */
package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * @author OEM
 *
 */
public interface XulMenuitem extends XulComponent {
  
  public String getAcceltext();
  public void setAcceltext(String accel);
  
  public String getLabel();
  public void setLabel(String label);
  
  public String getAccesskey();
  public void setAccesskey(String accessKey);

  public boolean getDisabled();
  public void setDisabled(boolean disabled);
  
  public String getImage();
  public void setImage(String image);
  
  public boolean isSelected();
  
  public void setCommand(String command);
  public String getCommand();
  

}
