package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtTreeCols extends AbstractSwtXulContainer implements XulTreeCols {


    XulTree table;

    public SwtTreeCols(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
      super("treecols");
      table = (XulTree) parent;

      setManagedObject("empty");
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
