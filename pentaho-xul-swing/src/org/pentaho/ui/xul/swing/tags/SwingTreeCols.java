package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTreeCols extends AbstractSwingContainer implements XulTreeCols {

  XulTree table;

  public SwingTreeCols(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("treecols");
    table = (XulTree) parent;

    managedObject = "empty";
  }

  public void addColumn(XulTreeCol column) {
    this.addChild(column);
  }

  public XulTreeCol getColumn(int index) {
    return (XulTreeCol) this.getChildNodes().get(index);
  }

  public int getColumnCount() {
    return this.getChildNodes().size();
  }

  public XulTree getTree() {
    // TODO Auto-generated method stub
    return table;
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
