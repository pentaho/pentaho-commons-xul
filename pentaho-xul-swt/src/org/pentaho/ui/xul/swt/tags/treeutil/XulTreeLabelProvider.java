package org.pentaho.ui.xul.swt.tags.treeutil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.util.SwtXulUtil;
import org.pentaho.ui.xul.util.XulUtil;

public class XulTreeLabelProvider implements ILabelProvider {

  private XulTree tree;
  private XulDomContainer domContainer;
  private static Log logger = LogFactory.getLog(XulTreeLabelProvider.class);
  
  public XulTreeLabelProvider(XulTree tree, XulDomContainer aDomContainer){
    this.tree = tree;
    this.domContainer = aDomContainer;
  }
  
  public Image getImage(Object item) {
    String src = ((XulTreeItem) item).getImage();

    Display display = ((TreeViewer) tree.getManagedObject()).getTree().getDisplay();
    return SwtXulUtil.getCachedImage(src, domContainer, display);
  }

  public String getText(Object item) {
    XulTreeItem treeitem = (XulTreeItem)item;
    if (treeitem != null && treeitem.getRow() != null && treeitem.getRow().getCell(0) != null) {
      return treeitem.getRow().getCell(0).getLabel();
    } else {
      return null;
    }
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