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
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtTabPanel extends AbstractGwtXulContainer implements XulTabpanel {

  static final String ELEMENT_NAME = "tabpanel";

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, GwtTabPanel::new );
  }

  protected VerticalPanel verticalPanel;

  public GwtTabPanel() {
    this( Orient.VERTICAL );
  }

  public GwtTabPanel( Orient orient ) {
    super( ELEMENT_NAME );

    this.orientation = orient;
    verticalPanel = GwtVbox.createManagedPanel();
    verticalPanel.addStyleName( "xul-" + ELEMENT_NAME );

    container = verticalPanel;
    setManagedObject( container );
  }

  public void resetContainer() {
    for ( int i = 0; i < verticalPanel.getWidgetCount(); i++ ) {
      verticalPanel.remove( i );
    }

    verticalPanel = GwtVbox.createManagedPanel();
  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.resetContainer();
    super.replaceChild( oldElement, newElement );
  }

}
