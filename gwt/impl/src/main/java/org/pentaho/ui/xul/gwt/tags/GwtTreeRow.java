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
