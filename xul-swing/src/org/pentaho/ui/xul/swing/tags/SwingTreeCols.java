package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTreeCols extends SwingElement implements XulTreeCols {

  XulTree table;

  public SwingTreeCols(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("treecols");
    table = (XulTree) parent;

    children = new ArrayList<XulComponent>();
    managedObject = "empty";
  }

  public void addColumn(XulTreeCol column) {
    // TODO Auto-generated method stub

  }

  public XulTreeCol getColumn(int index) {
    return (XulTreeCol) this.children.get(index);
  }

  public int getColumnCount() {
    return this.children.size();
  }

  public XulTree getTree() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void layout() {
    table.setColumns(this);
    initialized = true;
  }
}
