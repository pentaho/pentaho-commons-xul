package org.pentaho.ui.xul.gwt.tags;

import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeRow extends AbstractGwtXulComponent implements XulTreeRow {

  public static void register() {
    GwtXulParser.registerHandler("treerow",
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeRow();
      }
    });
  }
  
  List<XulTreeCell> cells;
  
  public GwtTreeRow() {
    super("treerow");
  }

  public void layout() {
    // setup XulTreeCells
    cells = (List) getElementsByTagName("treecell");
  }
  
  public void addCell(XulTreeCell cell) {
    // TODO Auto-generated method stub
    
  }

  public void addCellText(int index, String text) {
    // TODO Auto-generated method stub
    
  }

  public XulTreeCell getCell(int index) {
    return cells.get(index);
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
    // TODO Auto-generated method stub
    
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
