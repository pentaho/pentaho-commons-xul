/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags.treeutil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class XulTreeLabelProvider implements ILabelProvider {

  private XulTree tree;
  private XulDomContainer domContainer;
  private static Log logger = LogFactory.getLog( XulTreeLabelProvider.class );

  public XulTreeLabelProvider( XulTree tree, XulDomContainer aDomContainer ) {
    this.tree = tree;
    this.domContainer = aDomContainer;
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

  public void addListener( ILabelProviderListener arg0 ) {
  }

  public void dispose() {
  }

  public boolean isLabelProperty( Object arg0, String arg1 ) {
    return false;
  }

  public void removeListener( ILabelProviderListener arg0 ) {
  }

}
