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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;

public class XulTreeContentProvider implements ITreeContentProvider {

  private XulTree tree;

  public XulTreeContentProvider( XulTree tree ) {
    this.tree = tree;
  }

  public Object[] getChildren( Object item ) {
    if ( ( (XulTreeItem) item ).getChildNodes().size() > 1 ) {
      return ( (XulTreeItem) item ).getChildNodes().get( 1 ).getChildNodes().toArray();
    } else {
      return null;
    }
  }

  public Object getParent( Object item ) {
    return ( (XulTreeItem) item ).getParent() != null ? ( (XulTreeItem) item ).getParent().getParent() : null;
  }

  public boolean hasChildren( Object item ) {
    return ( (XulTreeItem) item ).getChildNodes().size() > 1
        && ( (XulTreeItem) item ).getChildNodes().get( 1 ).getChildNodes().size() > 0;
  }

  public Object[] getElements( Object arg0 ) {
    return tree.getRootChildren().getChildNodes().toArray();
  }

  public void dispose() {
  }

  public void inputChanged( Viewer arg0, Object arg1, Object arg2 ) {
  }

}
