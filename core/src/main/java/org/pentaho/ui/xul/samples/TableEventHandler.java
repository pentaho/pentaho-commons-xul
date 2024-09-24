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

package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

import java.util.Vector;

public class TableEventHandler extends AbstractXulEventHandler {

  XulTree tree;

  public void setup() {
    tree = (XulTree) document.getElementById( "testTable" );
  }

  public void printTable() {
    Object[][] data = tree.getValues();
    for ( int i = 0; i < data.length; i++ ) {
      for ( int z = 0; z < data[i].length; z++ ) {
        System.out.print( data[i][z] + "," );
      }
      System.out.println( "" );
    }
  }

  public void printRow() {
    int[] coords = tree.getSelectedRows();
    String selectionType = tree.getSeltype();
    Object[][] data = tree.getValues();

    if ( selectionType.equalsIgnoreCase( "single" ) ) {
      for ( int z = 0; z < data[coords[0]].length; z++ ) {
        System.out.print( data[coords[0]][z] + "," );
      }
      System.out.println( "" );

    } else if ( selectionType.equalsIgnoreCase( "multiple" ) ) {
      for ( int i = 0; i < coords.length; i++ ) {
        int idx = coords[i];
        for ( int z = 0; z < data[idx].length; z++ ) {
          System.out.print( data[idx][z] + "," );
        }
        System.out.println( "" );
      }
      System.out.println( "" );
    } else {
      // cell
      int[] cellPos = tree.getActiveCellCoordinates();
      System.out.println( data[cellPos[1]][cellPos[0]] );
    }

  }

  public void removeRow() {
    tree.removeTreeRows( tree.getSelectedRows() );
  }

  public void newRow() {
    try {

      XulTreeRow row = tree.getRootChildren().addNewRow();
      row.addCellText( 0, "false" );
      row.addCellText( 1, "test" );
      row.addCellText( 2, "fool" );
      row.addCellText( 3, "foo, bar, baz" );
      row.addCellText( 4, "fool" );

      XulTreeRow newRow = (XulTreeRow) document.createElement( "treerow" );

      XulTreeCell newCell = (XulTreeCell) document.createElement( "treecell" );
      newCell.setValue( Boolean.FALSE );
      newRow.addCell( newCell );

      newCell = (XulTreeCell) document.createElement( "treecell" );
      newCell.setLabel( "testing" );
      newRow.addCell( newCell );

      newCell = (XulTreeCell) document.createElement( "treecell" );
      newCell.setLabel( "testing 2" );
      newRow.addCell( newCell );

      newCell = (XulTreeCell) document.createElement( "treecell" );
      Vector vec = new Vector();
      vec.add( "foo" );
      vec.add( "bar" );
      vec.add( "baz" );
      newCell.setValue( vec );
      newRow.addCell( newCell );

      newCell = (XulTreeCell) document.createElement( "treecell" );
      newCell.setLabel( "testing 4" );
      newRow.addCell( newCell );

      tree.addTreeRow( newRow );
    } catch ( XulException e ) {
      System.out.println( "Error creating new row: " + e.getMessage() );
      e.printStackTrace( System.out );
    }
  }

  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setData( Object data ) {
    // TODO Auto-generated method stub

  }
}
