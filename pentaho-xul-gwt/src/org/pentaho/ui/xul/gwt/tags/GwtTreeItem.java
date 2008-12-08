package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeItem extends AbstractGwtXulComponent implements XulTreeItem {

  public static void register() {
    GwtXulParser.registerHandler("treeitem",
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeItem();
      }
    });
  }
  
  XulTreeRow row;
  
  public GwtTreeItem() {
    super("treeitem");
  }

  public void layout() {
    // get XulTreeRow
    row = (XulTreeRow)getElementsByTagName("treerow").get(0);
  }
  
  public XulTreeRow getRow() {
    return row;
  }

  public XulTree getTree() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isContainer() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void remove() {
    // TODO Auto-generated method stub
    
  }

  public void setContainer(boolean isContainer) {
    // TODO Auto-generated method stub
    
  }

  public void setEmpty(boolean empty) {
    // TODO Auto-generated method stub
    
  }

  public void setRow(XulTreeRow row) {
    // TODO Auto-generated method stub
    
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }
}
