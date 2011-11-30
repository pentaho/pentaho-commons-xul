package org.pentaho.ui.xul.gwt.tags;

import java.beans.PropertyChangeListener;
import java.util.List;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TreeItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.tags.util.TreeItemWidget;
import org.pentaho.ui.xul.stereotype.Bindable;

public class GwtTreeItem extends AbstractGwtXulContainer implements XulTreeItem {
  private boolean expanded;
  private String image;
  private Object obj;

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

  @Override
  public void layout() {
    super.layout();
  }

  public XulTreeRow getRow() {
    List list = getElementsByTagName("treerow");
    if (list.size() > 0) {
      return (XulTreeRow)list.get(0);
    }
    return null;
  }

  public XulTree getTree() {
    XulTree tree = null;
    XulComponent parent = getParent();
    while (parent != null) {
      if (parent instanceof XulTree) {
        tree = (XulTree) parent;
        break;
      }
      parent = parent.getParent();
    }
    return tree;
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


  @Bindable
  public String getImage() {
    return image;
  }

  @Bindable
  public void setImage(String src) {
    String oldSrc = this.image;
    this.image = src;
    firePropertyChange("image", oldSrc, src);
  }

  @Bindable
  public boolean isExpanded() {
    return expanded;
  }

  @Bindable
  public void setExpanded(boolean expanded) {
    this.expanded = expanded;
      XulTree tree = getTree();
    if (tree != null) {
      tree.setTreeItemExpanded(this, expanded);
    }

    changeSupport.firePropertyChange("expanded", null, expanded);
  }


  public Object getBoundObject() {
    return obj;
  }

  public void setBoundObject(Object obj) {

    this.obj = obj;
  }

  // TODO: migrate into XulComponent
  @Deprecated
  public void addPropertyChangeListener(String prop, PropertyChangeListener listener){
    changeSupport.addPropertyChangeListener(prop, listener);
  }
}
