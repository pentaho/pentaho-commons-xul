package org.pentaho.ui.xul.components;

import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.util.ColumnType;

public interface XulTreeCol extends XulComponent {
  
  public void setEditable(boolean edit);
  public boolean isEditable();

  public void setFixed(boolean fixed);
  public boolean isFixed();
  
  public void setHidden(boolean hide);
  public boolean isHidden();
  
  public void setLabel(String label);
  public String getLabel();
  
  public void setPrimary(boolean primo);
  public boolean isPrimary();
  
  public void setSortActive(boolean sort);
  public boolean isSortActive();
  
  public void setSortDirection(String dir);
  public String getSortDirection();
  
  public void setSrc(String srcUrl);
  public String getSrc();
  
  public void setType(String type);
  public String getType();
  
  public void setWidth(int width);
  public int getWidth();
  
  public void autoSize();
  
  public String getCustomeditor();
  public void setCustomeditor(String customClass);
  
  public void setBinding(String binding);
  public String getBinding();

}
