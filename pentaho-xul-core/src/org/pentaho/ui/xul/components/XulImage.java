package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulImage extends XulComponent{
  public void setSrc(String src);
  public void setSrc(Object img);
  public String getSrc();
  public void refresh();
}

  