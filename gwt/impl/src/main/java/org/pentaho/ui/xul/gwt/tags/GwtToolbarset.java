/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.containers.XulToolbarset;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtToolbarset extends AbstractGwtXulContainer implements XulToolbarset {

  public GwtToolbarset() {
    super( "toolbarset" );
  }

  public static void register() {
    GwtXulParser.registerHandler( "toolbarset", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbar();
      }
    } );
  }

}
