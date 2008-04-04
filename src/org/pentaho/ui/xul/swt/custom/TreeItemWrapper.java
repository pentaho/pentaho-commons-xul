package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.swt.RowWidget;

public class TreeItemWrapper implements RowWidget {

  TreeItem item = null;
  XulTree parentTree = null;

  public TreeItemWrapper(XulTree parent){
    item = new TreeItem((Tree)parentTree.getManagedObject(), SWT.NONE); 
  }

  public Item getItem() {
    return item;
  }

  public void setText(int index, String text) {
    item.setText(index,text);
  }
  

}
