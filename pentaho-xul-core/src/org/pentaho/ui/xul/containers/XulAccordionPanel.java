package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulAccordionPanel extends XulContainer {

  void setTitle(String title);
  String getTitle();
  void setExpaded(boolean expand);
  boolean isExpanded();
  
}
