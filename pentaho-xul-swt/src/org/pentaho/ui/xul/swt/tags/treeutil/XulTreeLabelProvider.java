package org.pentaho.ui.xul.swt.tags.treeutil;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;

public class XulTreeLabelProvider implements ILabelProvider {

  private XulTree tree;
  public XulTreeLabelProvider(XulTree tree){
    this.tree = tree;
  }
  
  public Image getImage(Object arg0) {
    return null;
  }

  public String getText(Object item) {
    return ((XulTreeItem) item).getRow().getCell(0).getLabel();
  }

  public void addListener(ILabelProviderListener arg0) {
  }

  public void dispose() {
  }

  public boolean isLabelProperty(Object arg0, String arg1) {
    return false;
  }

  public void removeListener(ILabelProviderListener arg0) {
  }

}