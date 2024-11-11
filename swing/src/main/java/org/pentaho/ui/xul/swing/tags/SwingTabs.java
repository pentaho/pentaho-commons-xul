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
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

public class SwingTabs extends AbstractSwingContainer implements XulTabs {
  public SwingTabs( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "tabs" );
    setManagedObject( "empty" );
  }

  public SwingTab getTabByIndex( int index ) {
    return (SwingTab) this.getChildNodes().get( index );
  }

  @Override
  public void removeChild( Element ele ) {
    int idx = getChildNodes().indexOf( ele );
    super.removeChild( ele );
    ( (XulTabbox) getParent() ).removeTab( idx );
    ( (SwingTabbox) getParent() ).layout();
  }

  @Override
  public void addChild( Element comp ) {
    super.addChild( comp );
    if ( initialized ) {
      ( (SwingTabbox) getParent() ).layout();
    }
  }

  @Override
  public void addChildAt( Element comp, int pos ) {
    super.addChildAt( comp, pos );
    if ( initialized ) {
      ( (SwingTabbox) getParent() ).layout();
    }
  }

  public int getTabCount() {
    return this.getChildNodes().size();
  }

}
