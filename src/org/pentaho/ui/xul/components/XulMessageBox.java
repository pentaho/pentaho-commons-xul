package org.pentaho.ui.xul.components;

public interface XulMessageBox {
  
  public void setTitle(String title);
  public String getTitle();
  public void setMessage(String message);
  public String getMessage();
  public void setButtons(Object[] buttons);
  public Object[] getButtons();
  public void setIcon(Object icon);
  public Object getIcon();
  public int open();

}
