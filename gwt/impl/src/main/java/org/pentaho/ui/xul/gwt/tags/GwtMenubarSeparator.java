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

import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtMenubarSeparator extends AbstractGwtXulComponent implements XulMenuseparator {

  public GwtMenubarSeparator() {
    super( "menubarseparator" );
  }

  public static void register() {
    GwtXulParser.registerHandler( "menubarseparator", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenubarSeparator();
      }
    } );
  }

}
