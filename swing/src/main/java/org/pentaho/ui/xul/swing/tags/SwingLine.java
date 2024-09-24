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

import java.awt.Dimension;

import javax.swing.JSeparator;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLine;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * User: nbaker Date: 6/24/13
 */
public class SwingLine extends SwingElement implements XulLine {
  public SwingLine( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "line" );
    JSeparator sep = new JSeparator( JSeparator.HORIZONTAL );
    sep.setMinimumSize( new Dimension( 10, 10 ) );

    setManagedObject( sep );

  }

}
