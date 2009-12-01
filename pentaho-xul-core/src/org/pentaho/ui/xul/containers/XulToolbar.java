package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulToolbar extends XulContainer{
  
  public enum ToolbarMode{ICONS, TEXT, FULL}
  
  void setMode(String mode);

  ToolbarMode getMode();
  
  String getToolbarName();
  
  void setToolbarName(String name);
  
}

  