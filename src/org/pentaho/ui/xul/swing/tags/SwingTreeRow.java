package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTreeRow extends SwingElement implements XulTreeRow {
  XulTreeItem treeItem;

  private XulTree tree;

  public SwingTreeRow(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("treerow");
    managedObject = "empty";
  }

  public SwingTreeRow(XulComponent parent) {
    super("treerow");
    managedObject = "empty";
  }

  public void addCell(XulTreeCell cell) {
    this.children.add(cell);
    super.addChild(cell);
  }

  private XulTree getTree() {
    if (tree == null) {
      tree = (SwingTree) this.getParent().getParent().getParent();
    }
    return tree;
  }

  public void addCellText(int index, String text) {

    SwingTreeCell cell = null;
    if (index < children.size()) {
      cell = (SwingTreeCell) children.get(index);
    } else {
      cell = new SwingTreeCell(this);
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
    if (index < this.children.size()) {
      return (SwingTreeCell) this.children.get(index);
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

}
