package org.pentaho.ui.xul.swt.tags.treeutil;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.swt.tags.SwtTree;

public class XulTableContentProvider implements IStructuredContentProvider {

  private XulTree xulTree;
  public XulTableContentProvider(XulTree tree){
    this.xulTree = tree;
  }
  
  public Object[] getElements(Object arg0) {
    return xulTree.getRootChildren().getChildNodes().toArray();
  }

  public void dispose() {
  }

  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
  }

}