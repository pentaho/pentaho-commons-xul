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

package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeChildren extends AbstractGwtXulContainer implements XulTreeChildren {

  public static void register() {
    GwtXulParser.registerHandler( "treechildren", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeChildren();
      }
    } );
  }

  public GwtTreeChildren() {
    super( "treechildren" );
  }

  public void addChildAt( Element element, int idx ) {
    super.addChildAt( element, idx );
    try {
      if ( getTree() != null ) {
        ( (GwtTree) getTree() ).updateUI();
      }
    } catch ( Throwable t ) {
      //ignored
    }
  }

  public void addChild( Element element ) {
    super.addChild( element );
    try {
      if ( getTree() != null ) {
        ( (GwtTree) getTree() ).updateUI();
      }
    } catch ( Throwable t ) {
      //ignored
    }
  }

  public void addItem( XulTreeItem item ) {
    addChild( item );
  }

  public XulTreeRow addNewRow() {
    try {
      XulTreeRow row = (XulTreeRow) getDocument().createElement( "treerow" );
      XulTreeItem item = (XulTreeItem) getDocument().createElement( "treeitem" );
      item.setRow( row );
      addItem( item );
      return row;
    } catch ( XulException e ) {
      e.printStackTrace();
    }
    return null;
  }

  public XulTreeItem getItem( int rowIndex ) {
    return (XulTreeItem) children.get( rowIndex );
  }

  public int getItemCount() {
    if ( children != null ) {
      return children.size();
    } else {
      return 0;
    }
  }

  public boolean isAlternatingbackground() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void removeItem( XulTreeItem item ) {
    removeChild( item );
  }

  public void removeItem( int rowIndex ) {
    removeChild( children.get( rowIndex ) );
  }

  public void setAlternatingbackground( boolean alt ) {
    // TODO Auto-generated method stub

  }

  public XulTree getTree() {
    XulTree tree = null;
    XulComponent parent = getParent();
    while ( parent != null ) {
      if ( parent instanceof XulTree ) {
        tree = (XulTree) parent;
        break;
      }
      parent = parent.getParent();
    }
    return tree;
  }

  public void removeAll() {
    children.clear();
    if ( getTree() != null ) {
      ( (GwtTree) getTree() ).updateUI();
    }
  }

}
