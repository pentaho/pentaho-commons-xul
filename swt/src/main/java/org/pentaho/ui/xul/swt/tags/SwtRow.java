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
