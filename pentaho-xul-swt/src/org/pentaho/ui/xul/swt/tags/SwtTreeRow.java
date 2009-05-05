package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Item;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.RowWidget;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtTreeRow extends AbstractSwtXulContainer implements XulTreeRow {

    XulTreeItem treeItem;

    private XulTree tree;

    public SwtTreeRow(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
      super("treerow");
      managedObject = "empty";
    }

    public SwtTreeRow(XulComponent parent) {
      super("treerow");
      managedObject = "empty";
    }

    public void addCell(XulTreeCell cell) {
      super.addChild(cell);
    }

    private XulTree getTree() {
      if (tree == null) {
        tree = (SwtTree) this.getParent().getParent().getParent();
      }
      return tree;
    }

    public void addCellText(int index, String text) {

      SwtTreeCell cell = null;
      if (index < getChildNodes().size()) {
        cell = (SwtTreeCell) getChildNodes().get(index);
      } else {
        cell = new SwtTreeCell(this);
        this.addCell(cell);
      }

      switch (getTree().getColumns().getColumn(index).getColumnType()) {
        case CHECKBOX:
        case COMBOBOX:
          cell.setValue(text);
          break;
        default:
          cell.setLabel(text);
      }
      getTree().update();
      layout();
    }

    public void makeCellEditable(int index) {
      // TODO Auto-generated method stub
    }

    @Override
    public void layout() {
      initialized = true;
    }

    public XulTreeCell getCell(int index) {
      if (index < this.getChildNodes().size()) {
        return (SwtTreeCell) this.getChildNodes().get(index);
      } else {
        return null;
      }

    }

    public int getSelectedColumnIndex() {
      return 0;
    }

    public void remove() {
      // TODO Auto-generated method stub

    }

    public void setParentTreeItem(XulTreeItem item) {
      
          // TODO Auto-generated method stub 
        
    }

  }
