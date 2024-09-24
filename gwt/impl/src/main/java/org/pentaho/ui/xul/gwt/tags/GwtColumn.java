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

import org.pentaho.ui.xul.containers.XulColumn;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtColumn extends AbstractGwtXulContainer implements XulColumn {

  public static void register() {
    GwtXulParser.registerHandler( "column", // $ NON-NLS-1$
        new GwtXulHandler() {
          public Element newInstance() {
            return new GwtColumn();
          }
        } );
  }

  public GwtColumn() {
    super( "column" ); //$NON-NLS-1$
    setManagedObject( "empty" );
  }

  public void layout() {

  }
}
