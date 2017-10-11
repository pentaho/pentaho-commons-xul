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

package org.pentaho.ui.xul.swing.tags;

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
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

public class SwingTreeItem extends AbstractSwingContainer implements XulTreeItem {
  private static final Log logger = LogFactory.getLog( SwingTreeItem.class );

  private XulTreeRow row;
  private XulTreeChildren treeChildren; // Hierachical tree
  private Reference boundObjectRef;

  public SwingTreeItem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "treeitem" );
    setManagedObject( "empty" );
  }

  public SwingTreeItem( XulTreeRow row ) {
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

  public SwingTreeItem( XulTreeChildren parent ) {
    super( "treeitem" );
    setManagedObject( "empty" );
  }

  public XulTreeRow getRow() {
    return row;
  }

  public XulTree getTree() {
    // TODO Auto-generated method stub
    return null;
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
          this.row = (SwingTreeRow) comp;
        } else {
          this.treeChildren = (XulTreeChildren) comp;
        }
      }
    } else {
      // table
      for ( Element comp : getChildNodes() ) { // should be the only one in there
        if ( comp instanceof XulTreeRow ) { // more of an assert, should be true;
          this.row = (SwingTreeRow) comp;
        }
      }
    }

    initialized = true;
  }

  public String getImage() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setImage( String src ) {
    // TODO Auto-generated method stub

  }

  public boolean isExpanded() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setExpanded( boolean expanded ) {
    // TODO Auto-generated method stub

  }

  public Object getBoundObject() {
    return boundObjectRef.get();
  }

  public void setBoundObject( Object obj ) {
    boundObjectRef = new WeakReference( obj );
  }

  @Override
  public void setClassname( String classname ) {
    // Classname does not apply to Swing
  }

  @Override
  public String getClassname() {
    return null;
  }

}
