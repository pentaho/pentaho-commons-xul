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

/**
 * 
 */

package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;

import javax.swing.Box;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSpacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author nbaker
 * 
 */
public class SwingSpacer extends SwingElement implements XulSpacer {
  private Component strut;

  public SwingSpacer( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "spacer" );
  }

  public void setWidth( int size ) {
    strut = Box.createHorizontalStrut( size );
    setManagedObject( strut );
  }

  public void setHeight( int size ) {
    strut = Box.createVerticalStrut( size );
    setManagedObject( strut );
  }

  @Override
  public void setFlex( int flex ) {
    super.setFlex( flex );
    setManagedObject( Box.createGlue() );
  }

  public void layout() {
  }
}
