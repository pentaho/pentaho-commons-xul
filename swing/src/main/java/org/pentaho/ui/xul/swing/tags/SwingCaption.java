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


/**
 * 
 */

package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author nbaker
 * 
 */
public class SwingCaption extends SwingElement implements XulCaption {
  private String caption;

  public SwingCaption( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "caption" );
    setManagedObject( caption );
  }

  public String getLabel() {
    return caption;
  }

  public void setLabel( String caption ) {
    this.caption = caption;
    setManagedObject( caption );
  }

  public void layout() {
  }
}
