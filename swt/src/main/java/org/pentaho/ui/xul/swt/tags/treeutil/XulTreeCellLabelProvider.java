/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.swt.tags.treeutil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.swt.tags.SwtTreeItem;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class XulTreeCellLabelProvider extends ColumnLabelProvider {

  private XulTree tree;
  private XulDomContainer domContainer;
  private static Log logger = LogFactory.getLog( XulTreeCellLabelProvider.class );

  public XulTreeCellLabelProvider( XulTree tree, XulDomContainer aDomContainer ) {
    this.tree = tree;
    this.domContainer = aDomContainer;
  }

  @Override
  public String getToolTipText( Object element ) {
    return ( (SwtTreeItem) element ).getTooltiptext();
  }

  public Image getImage( Object item ) {
    String src = ( (XulTreeItem) item ).getImage();
    Display display = ( (TreeViewer) tree.getManagedObject() ).getTree().getDisplay();
    return SwtXulUtil.getCachedImage( src, domContainer, display );
  }

  public String getText( Object item ) {
    XulTreeItem treeitem = (XulTreeItem) item;
    if ( treeitem != null && treeitem.getRow() != null && treeitem.getRow().getCell( 0 ) != null ) {
      return treeitem.getRow().getCell( 0 ).getLabel();
    } else {
      return null;
    }
  }

  @Override
  public int getToolTipDisplayDelayTime( Object object ) {
    return 300;
  }

  @Override
  public int getToolTipTimeDisplayed( Object object ) {
    return 4000;
  }

  @Override
  public boolean useNativeToolTip( Object object ) {
    return true;
  }

}
