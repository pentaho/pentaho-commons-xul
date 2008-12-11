package org.pentaho.ui.xul.gwt.tags;

import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeItem extends AbstractGwtXulContainer implements XulTreeItem {

  public static void register() {
    GwtXulParser.registerHandler("treeitem",
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeItem();
      }
    });
  }
  
  public GwtTreeItem() {
    super("treeitem");
  }
  
  public XulTreeRow getRow() {
    List list = getElementsByTagName("treerow");
    if (list.size() > 0) {
      return (XulTreeRow)list.get(0);
    }
    return null;
  }

  public XulTree getTree() {
    if (getParent() != null) { // tree children
      return (XulTree)getParent().getParent();
    }
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
    if (getRow() != null) {
      this.removeChild(getRow());
    }
    this.addChild(row);
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }
}
