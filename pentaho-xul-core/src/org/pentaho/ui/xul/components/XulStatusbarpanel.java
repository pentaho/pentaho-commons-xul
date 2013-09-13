package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * A special panel placed inside Statusbar elements that can display an image or text, but not both.
 * 
 * @author nbaker
 */

public interface XulStatusbarpanel extends XulComponent {
  public void setLabel(String label);
  public String getLabel();
  public void setImage(String image);
  public String getImage();
}

  