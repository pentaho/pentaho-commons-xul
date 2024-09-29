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

import org.pentaho.ui.xul.containers.XulBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtBox extends AbstractGwtXulContainer implements XulBox {

  static final String ELEMENT_NAME = "box"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtBox();
      }
    } );
  }

  protected com.google.gwt.user.client.ui.Panel box;

  public GwtBox() {
    super( "box" );
    orientation = Orient.HORIZONTAL;
    updateOrientation();
  }

  private void updateOrientation() {
    if ( getOrientation() == Orient.HORIZONTAL ) {
      box = new HorizontalPanel();
    } else {
      box = new VerticalPanel();
    }
    setManagedObject( box );
  }

  public void setOrient( String orient ) {
    super.setOrient( orient );
    updateOrientation();
  }
}
