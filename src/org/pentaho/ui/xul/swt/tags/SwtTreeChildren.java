package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtTreeChildren extends SwtElement implements XulTreeChildren {

  protected XulTree parent = null;
  
  protected List <XulTreeItem> items;
  
  private boolean alternateBackgroundColors = false;
  
  public SwtTreeChildren(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    this.parent = (XulTree)parent;
    items = new ArrayList <XulTreeItem>();
  }
  
  public void addItem(XulTreeItem column){
    items.add(column);
  }
  
  public boolean isHierarchical(){
    return parent.isHierarchical();
  }
  
  public XulTree getTree(){
    return parent;
  }
  
  public boolean isAlternatingbackground() {
    return alternateBackgroundColors;
  }

  public void setAlternatingbackground(boolean alt) {
    alternateBackgroundColors = alt;
  }

}
