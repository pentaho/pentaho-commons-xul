package org.pentaho.ui.xul.swt.tags.treeutil;

import java.io.InputStream;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.swt.tags.SwtButton;

public class XulTreeLabelProvider implements ILabelProvider {

  private XulTree tree;
  public XulTreeLabelProvider(XulTree tree){
    this.tree = tree;
  }
  
  public Image getImage(Object item) {
    String src = ((XulTreeItem) item).getImage();
    InputStream in = null;
    try{
      if(src != null){
        in = this.getClass().getClassLoader().getResourceAsStream(src);
      }
    
      return src == null || in == null ? null : new Image(((TreeViewer) tree.getManagedObject()).getTree().getDisplay(), in);
    } finally{
      try{
        in.close();
      } catch(Exception ignored){}
    }
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