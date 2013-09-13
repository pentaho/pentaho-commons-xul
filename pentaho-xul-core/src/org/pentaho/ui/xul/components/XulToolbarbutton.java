package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulToolbarbutton extends XulButton{

  public void setDownimage(String img);
  public String getDownimage();
  public String getDownimagedisabled();
  public void setDownimagedisabled(String img);
  
  /**
   * Sets the selected state of the button. If this button is a member of a button group, 
   * the other buttons will be de-selected.
   * @param selected
   * @param fireEvent fire command object on this call
   */
  public void setSelected(boolean selected, boolean fireEvent);
}

  