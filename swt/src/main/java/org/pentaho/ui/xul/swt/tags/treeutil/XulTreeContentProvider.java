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
