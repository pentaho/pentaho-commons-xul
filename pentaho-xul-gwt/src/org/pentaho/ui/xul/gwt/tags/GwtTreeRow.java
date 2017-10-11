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
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeRow extends AbstractGwtXulContainer implements XulTreeRow {

  public static void register() {
    GwtXulParser.registerHandler( "treerow", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeRow();
      }
    } );
  }

  private GwtTree getTree() {
    XulComponent parent = this;
    int x = 3;
    while ( x-- > 0 ) {
      if ( ( parent = parent.getParent() ) == null ) {
        return null;
      }
    }
    return (GwtTree) parent;
  }

  public GwtTreeRow() {
    super( "treerow" );
  }

  public void addCell( XulTreeCell cell ) {
    addChild( cell );
  }

  public void addCellText( int index, String text ) {
    GwtTreeCell cell = null;
    if ( index < getChildNodes().size() ) {
      cell = (GwtTreeCell) getChildNodes().get( index );
    } else {
      cell = new GwtTreeCell();
      this.addCell( cell );
    }
    XulTree tree = getTree();
    if ( tree == null ) {
      cell.setLabel( text );
      return;
    }
    switch ( tree.getColumns().getColumn( index ).getColumnType() ) {
      case CHECKBOX:
      case COMBOBOX:
        cell.setValue( text );
        break;
      default:
        cell.setLabel( text );
    }
    tree.update();
    layout();
  }

  public XulTreeCell getCell( int index ) {
    return (XulTreeCell) children.get( index );
  }

  public int getSelectedColumnIndex() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void makeCellEditable( int index ) {
    // TODO Auto-generated method stub

  }

  public void remove() {
    // TODO Auto-generated method stub

  }

  public void setParentTreeItem( XulTreeItem item ) {
    super.setParent( item );
  }

}
