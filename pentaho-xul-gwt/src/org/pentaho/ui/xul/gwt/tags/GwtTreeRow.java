package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeRow extends AbstractGwtXulContainer implements XulTreeRow {

  public static void register() {
    GwtXulParser.registerHandler("treerow",
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeRow();
      }
    });
  }
  
  public GwtTreeRow() {
    super("treerow");
  }

  public void addCell(XulTreeCell cell) {
    addChild(cell);
  }

  public void addCellText(int index, String text) {
    GwtTreeCell cell = new GwtTreeCell();
    cell.setLabel(text);
    addCell(cell);
  }

  public XulTreeCell getCell(int index) {
    return (XulTreeCell) children.get(index);
  }

  public int getSelectedColumnIndex() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void makeCellEditable(int index) {
    // TODO Auto-generated method stub
    
  }

  public void remove() {
    // TODO Auto-generated method stub
    
  }

  public void setParentTreeItem(XulTreeItem item) {
    super.setParent(item);
  }


}
