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
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtDomElement;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtMenupopup extends AbstractGwtXulContainer implements XulMenupopup {

  static final String ELEMENT_NAME = "menupopup"; //$NON-NLS-1$

  public GwtMenupopup() {
    super( ELEMENT_NAME );
    setManagedObject( "empty" );
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenupopup();
      }
    } );
  }

  public void resetContainer() {
    super.resetContainer();
    final XulComponent component = getParent();
    if ( component instanceof GwtDomElement ) {
      final AbstractGwtXulContainer parent = (AbstractGwtXulContainer) component;
      parent.resetContainer();
    }
  }
}
