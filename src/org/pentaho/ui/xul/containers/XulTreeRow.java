package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.components.XulTreeCell;

public interface XulTreeRow extends XulContainer {
  
    public void addCell(XulTreeCell cell);

}
