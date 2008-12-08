package org.pentaho.ui.xul.gwt.tags;

import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeChildren extends AbstractGwtXulComponent implements XulTreeChildren {

  public static void register() {
    GwtXulParser.registerHandler("treechildren",
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeChildren();
      }
    });
  }
  
  List<XulTreeItem> items;
  
  public GwtTreeChildren() {
    super("treechildren");
  }
  
  public void layout() {
    items = (List) getElementsByTagName("treeitem");
  }

  public void addItem(XulTreeItem item) {
    // TODO Auto-generated method stub
    
  }

  public XulTreeRow addNewRow() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulTreeItem getItem(int rowIndex) {
    return items.get(rowIndex);
  }

  public int getItemCount() {
    return items.size();
  }

  public XulTree getTree() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isAlternatingbackground() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void removeAll() {
    // TODO Auto-generated method stub
    
  }

  public void removeItem(XulTreeItem item) {
    // TODO Auto-generated method stub
    
  }

  public void removeItem(int rowIndex) {
    // TODO Auto-generated method stub
    
  }

  public void setAlternatingbackground(boolean alt) {
    // TODO Auto-generated method stub
    
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }
  
}
