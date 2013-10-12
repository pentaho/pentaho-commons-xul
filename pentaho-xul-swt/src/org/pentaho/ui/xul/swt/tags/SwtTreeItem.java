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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtTreeItem extends AbstractSwtXulContainer implements XulTreeItem {

  private static final Log logger = LogFactory.getLog( SwtTreeItem.class );

  private XulTreeRow row;
  private XulTreeChildren treeChildren; // Hierachical tree
  private String image;
  private XulDomContainer domContainer;
  private boolean expanded;
  private Reference boundObjectRef;

  public SwtTreeItem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "treeitem" );
    this.domContainer = domContainer;
    setManagedObject( "empty" );
  }

  public SwtTreeItem( XulTreeRow row ) {
    super( "treeitem" );

    try {
      this.element = DocumentFactory.createElement( "treeitem", this );
    } catch ( XulException e ) {
      throw new IllegalArgumentException( "error creating treeitem", e );
    }

    super.addChild( row );
    this.row = row;
    setManagedObject( "empty" );
  }

  public SwtTreeItem( XulTreeChildren parent ) {
    super( "treeitem" );
    setManagedObject( "empty" );
  }

  public XulTreeRow getRow() {
    return row;
  }

  public boolean isContainer() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void remove() {
    // TODO Auto-generated method stub

  }

  public void setContainer( boolean isContainer ) {
    // TODO Auto-generated method stub

  }

  public void setEmpty( boolean empty ) {
    // TODO Auto-generated method stub

  }

  public void setRow( XulTreeRow row ) {
    this.row = row;
    super.addChild( row );
  }

  @Override
  public void layout() {
    if ( getChildNodes().size() > 1 ) {
      // tree
      for ( Element comp : getChildNodes() ) { // should be the only one in there
        if ( comp instanceof XulTreeRow ) { // more of an assert, should be true;
          this.row = (SwtTreeRow) comp;
        } else {
          this.treeChildren = (XulTreeChildren) comp;
        }
      }
    } else {
      // table
      for ( Element comp : getChildNodes() ) { // should be the only one in there
        if ( comp instanceof XulTreeRow ) { // more of an assert, should be true;
          this.row = (SwtTreeRow) comp;
        }
      }
    }

    initialized = true;
  }

  public String getImage() {
    return image;
  }

  public void setImage( String src ) {
    this.image = src;

    if ( this.initialized ) {
      XulTree tree = getTree();
      if ( tree != null ) {
        tree.update();
      }
    }
  }

  @Override
  public void setTooltiptext( String tooltip ) {
    super.setTooltiptext( tooltip );
    if ( this.initialized ) {
      XulTree tree = getTree();
      if ( tree != null ) {
        tree.update();
      }
    }

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

  public void setXulDomContainer( XulDomContainer container ) {
    this.domContainer = container;
  }

  public boolean isExpanded() {
    return expanded;
  }

  public void setExpanded( boolean expanded ) {
    this.expanded = expanded;
    if ( this.initialized ) {
      XulTree tree = getTree();
      if ( tree != null ) {
        tree.setTreeItemExpanded( this, expanded );
      }
    }

    changeSupport.firePropertyChange( "expanded", null, expanded );
  }

  public Object getBoundObject() {
    if ( boundObjectRef != null ) {
      return boundObjectRef.get();
    }
    return null;
  }

  public void setBoundObject( Object obj ) {
    boundObjectRef = new WeakReference( obj );
  }

  @Override
  public void setClassname( String classname ) {
    // Classname does not apply to SWT
  }

  @Override
  public String getClassname() {
    return null;
  }

}
