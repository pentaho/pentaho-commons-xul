/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtTreeChildren extends AbstractSwtXulContainer implements XulTreeChildren {

  private XulTree tree;

  public SwtTreeChildren( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "treechildren" );

    setManagedObject( "empty" );

  }

  public SwtTreeChildren( XulComponent parent ) {
    super( "treechildren" );

    setManagedObject( "empty" );

  }

  public void addItem( XulTreeItem item ) {
    this.addChild( item );
  }

  public XulTreeRow addNewRow() {

    SwtTreeItem item = new SwtTreeItem( this );
    item.setRow( new SwtTreeRow( item ) );
    addChild( item );

    return item.getRow();
  }

  public XulTreeItem getItem( int rowIndex ) {
    return ( getChildNodes().size() > rowIndex ) ? (XulTreeItem) getChildNodes().get( rowIndex ) : null;
  }

  public XulTree getTree() {
    if ( tree == null ) {
      XulComponent c = getParent();

      while ( c != null ) {
        if ( c instanceof XulTree ) {
          tree = (XulTree) c;
          break;
        }

        c = c.getParent();
      }
    }
    return tree;
  }

  public boolean isAlternatingbackground() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public int getItemCount() {
    return this.getChildNodes().size();
  }

  public void removeAll() {
    while ( getChildNodes().size() > 0 ) {
      removeItem( 0, false );
    }
    getTree().update();
  }

  public void removeItem( XulTreeItem item ) {
    this.removeChild( item );
    getTree().update();
  }

  public void removeItem( int rowIndex, boolean refresh ) {
    this.removeChild( getChildNodes().get( rowIndex ) );
    if ( refresh ) {
      getTree().update();
    }

  }

  public void removeItem( int rowIndex ) {
    removeItem( rowIndex, true );
  }

  public void setAlternatingbackground( boolean alt ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onDomReady() {
    // TODO: move this into constructor once the "self" element is real
    // Due to many nested TreeItems with their own TreeChildren, we have to walk up the DOm
    // an unknown positions to find the tree.
    if ( tree == null ) {
      XulComponent c = getParent();

      while ( c != null ) {
        if ( c instanceof XulTree ) {
          tree = (XulTree) c;
          break;
        }

        c = c.getParent();
      }
    }
    layout();
  }

  @Override
  public void layout() {
    if ( getTree() != null ) {
      initialized = true;
    }
  }

}
