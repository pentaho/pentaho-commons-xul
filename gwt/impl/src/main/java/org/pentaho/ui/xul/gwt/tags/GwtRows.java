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


package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.containers.XulGrid;
import org.pentaho.ui.xul.containers.XulRow;
import org.pentaho.ui.xul.containers.XulRows;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtRows extends AbstractGwtXulContainer implements XulRows {

  public static void register() {
    GwtXulParser.registerHandler( "rows", //$NON-NLS-1$
        new GwtXulHandler() {
          public Element newInstance() {
            return new GwtRows();
          }
        } );
  }

  public GwtRows() {
    super( "rows" ); //$NON-NLS-1$
    setManagedObject( "empty" );
  }

  public void addRow( XulRow row ) {
    super.addChild( row );
  }

  public XulRow getRow( int index ) {
    return (XulRow) this.getChildNodes().get( index );
  }

  public int getRowCount() {
    return this.getChildNodes().size();
  }

  public XulGrid getGrid() {
    return (XulGrid) getParent();
  }

  public void layout() {

  }

}
