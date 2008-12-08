package org.pentaho.ui.xul.components;

import java.awt.Image;

import org.pentaho.ui.xul.XulComponent;

public interface XulImage extends XulComponent{
  public void setSrc(String src);
  public void setSrc(Image img);
  public String getSrc();
  public void refresh();
}

  