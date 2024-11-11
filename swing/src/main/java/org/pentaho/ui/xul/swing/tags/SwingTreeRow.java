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
