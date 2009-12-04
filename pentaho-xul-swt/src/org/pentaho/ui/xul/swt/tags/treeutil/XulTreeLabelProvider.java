package org.pentaho.ui.xul.swt.tags.treeutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;

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
      Image img = null;
      if(in == null){
        File f = new File(src);
        if(f.exists()){
          in = new FileInputStream(f);
          new Image(((TreeViewer) tree.getManagedObject()).getTree().getDisplay(), in);
        }
      } else {
        new Image(((TreeViewer) tree.getManagedObject()).getTree().getDisplay(), in);
      }
      return img; 
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally{
      try{
        in.close();
      } catch(Exception ignored){}
    }
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