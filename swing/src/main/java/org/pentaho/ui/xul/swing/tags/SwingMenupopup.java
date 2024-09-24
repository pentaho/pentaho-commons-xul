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

package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenupopup extends AbstractSwingContainer implements XulMenupopup {

  public SwingMenupopup( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menupopup" );
  }

  public void layout() {
    initialized = true;
    final XulComponent component = getParent();
    if ( component instanceof SwingElement ) {
      final SwingElement parent = (SwingElement) component;
      parent.layout();
    }
  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    resetContainer();
    super.replaceChild( oldElement, newElement );
  }

  public void resetContainer() {
    super.resetContainer();
    final XulComponent component = getParent();
    if ( component instanceof SwingElement ) {
      final SwingElement parent = (SwingElement) component;
      parent.resetContainer();
    }
  }
}
