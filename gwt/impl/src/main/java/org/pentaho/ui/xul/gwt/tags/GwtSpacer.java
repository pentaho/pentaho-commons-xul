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

import org.pentaho.ui.xul.components.XulSpacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.SimplePanel;

public class GwtSpacer extends AbstractGwtXulComponent implements XulSpacer {

  static final String ELEMENT_NAME = "spacer"; //$NON-NLS-1$

  private SimplePanel panel;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtSpacer();
      }
    } );
  }

  public GwtSpacer() {
    super( ELEMENT_NAME );
    panel = new SimplePanel();
    setManagedObject( panel );
  }

  public void setWidth( int size ) {
    panel.setWidth( size + "px" );
  }

  public void setHeight( int size ) {
    panel.setHeight( size + "px" );
  }

  public void layout() {
  }

}
