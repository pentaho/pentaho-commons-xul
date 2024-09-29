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

import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

public class GwtTabPanels extends AbstractGwtXulContainer implements XulTabpanels {

  static final String ELEMENT_NAME = "tabpanels"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTabPanels();
      }
    } );
  }

  public GwtTabPanels() {
    this( Orient.HORIZONTAL );
  }

  public GwtTabPanels( Orient orient ) {
    super( ELEMENT_NAME );
    setManagedObject( "empty" );
  }

  public XulTabpanel getTabpanelByIndex( int index ) {
    return ( index < getChildNodes().size() ) ? (GwtTabPanel) this.getChildNodes().get( index ) : null;
  }

  @Override
  public void removeChild( Element ele ) {
    int idx = getChildNodes().indexOf( ele );
    super.removeChild( ele );
    ( (XulTabbox) getParent() ).removeTabpanel( idx );
  }

  @Override
  public void layout() {
    initialized = true;
    if ( getParent() != null ) {
      ( (AbstractGwtXulComponent) getParent() ).layout();
    }
  }
}
