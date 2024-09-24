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

package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.containers.XulRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

/**
 * User: nbaker Date: Apr 14, 2009
 */
public class SwtRow extends AbstractSwtXulContainer implements XulColumns, XulRow {

  public SwtRow( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "row" );

    if ( parent != null && parent instanceof SwtRows ) {
      setManagedObject( ( (SwtRows) parent ).getGrid().getManagedObject() );
    } else {
      setManagedObject( "empty" );
    }
  }

  @Override
  public void layout() {

  }

  public Composite getGrid() {
    if ( getParent() != null && getParent().getParent() != null ) {
      return (Composite) getParent().getParent().getManagedObject();
    }
    return null;
  }

  @Override
  public void addChild( Element e ) {
    super.addChild( e );
    if ( getGrid() != null ) {
      ( (Control) ( (XulComponent) e ).getManagedObject() ).setParent( getGrid() );
    }
  }

  @Override
  public void addChildAt( Element c, int pos ) {
    // TODO Auto-generated method stub
    super.addChildAt( c, pos );
    if ( getGrid() instanceof Composite ) {
      ( (Control) ( (XulComponent) c ).getManagedObject() ).setParent( getGrid() );
    }
  }

}
