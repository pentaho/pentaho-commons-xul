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
