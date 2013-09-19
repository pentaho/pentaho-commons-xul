package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.components.XulTreeCol;

public interface XulTreeCols extends XulContainer {
  
  public boolean isHierarchical();

  public XulTree getTree();
  
  public void addColumn(XulTreeCol column);
  
  public XulTreeCol getColumn(int index);
  
  public int getColumnCount();

}
