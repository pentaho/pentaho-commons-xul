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

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

public class SwingTreeRow extends AbstractSwingContainer implements XulTreeRow {
  XulTreeItem treeItem;

  private XulTree tree;

  public SwingTreeRow( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "treerow" );
    setManagedObject( "empty" );
  }

  public SwingTreeRow( XulComponent parent ) {
    super( "treerow" );
    setManagedObject( "empty" );
  }

  public void addCell( XulTreeCell cell ) {
    super.addChild( cell );
  }

  private XulTree getTree() {
    if ( tree == null ) {
      tree = (SwingTree) this.getParent().getParent().getParent();
    }
    return tree;
  }

  public void addCellText( int index, String text ) {

    SwingTreeCell cell = null;
    if ( index < getChildNodes().size() ) {
      cell = (SwingTreeCell) getChildNodes().get( index );
    } else {
      cell = new SwingTreeCell( this );
      this.addCell( cell );
    }

    switch ( getTree().getColumns().getColumn( index ).getColumnType() ) {
      case CHECKBOX:
      case COMBOBOX:
        cell.setValue( text );
        break;
      default:
        cell.setLabel( text );
    }
    layout();
  }

  public void makeCellEditable( int index ) {
    // TODO Auto-generated method stub
  }

  @Override
  public void layout() {
    initialized = true;
  }

  public XulTreeCell getCell( int index ) {
    if ( index < this.getChildNodes().size() ) {
      return (SwingTreeCell) this.getChildNodes().get( index );
    } else {
      return null;
    }

  }

  public int getSelectedColumnIndex() {
    return 0;
  }

  public void remove() {
    // TODO Auto-generated method stub

  }

  public void setParentTreeItem( XulTreeItem item ) {

    // TODO Auto-generated method stub

  }

}
