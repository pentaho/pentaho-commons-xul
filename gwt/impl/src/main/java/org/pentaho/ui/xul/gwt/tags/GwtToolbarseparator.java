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

import org.pentaho.ui.xul.components.XulToolbarseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtToolbarseparator extends AbstractGwtXulComponent implements XulToolbarseparator {

  public GwtToolbarseparator() {
    super( "toolbarseparator" );
  }

  public static void register() {
    GwtXulParser.registerHandler( "toolbarseparator", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbarseparator();
      }
    } );
  }

}
