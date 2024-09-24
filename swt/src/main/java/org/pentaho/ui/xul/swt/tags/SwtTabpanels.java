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

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtTabpanels extends AbstractSwtXulContainer implements XulTabpanels {

  private XulTabbox tabbox;

  public SwtTabpanels( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "tabpanels" );
    tabbox = (XulTabbox) parent;
    setManagedObject( "empty" );
  }

  protected XulTabbox getTabbox() {
    return tabbox;
  }

  public XulTabpanel getTabpanelByIndex( int index ) {
    return (XulTabpanel) this.getChildNodes().get( index );
  }

  @Override
  public void removeChild( Element ele ) {
    int idx = getChildNodes().indexOf( ele );
    super.removeChild( ele );
    ( (XulTabbox) getParent() ).removeTabpanel( idx );
  }

  @Override
  public void addChild( Element c ) {
    super.addChild( c );
    if ( getParent() != null ) {
      ( (XulTabbox) getParent() ).addTabpanel( this.getChildNodes().indexOf( c ) );
    }
  }

  @Override
  public void addChildAt( Element c, int pos ) {
    super.addChildAt( c, pos );
    if ( getParent() != null ) {
      ( (XulTabbox) getParent() ).addTabpanel( pos );
      ( (SwtTabbox) getParent() ).layout();
    }
  }

  @Override
  public void layout() {
  }
}
