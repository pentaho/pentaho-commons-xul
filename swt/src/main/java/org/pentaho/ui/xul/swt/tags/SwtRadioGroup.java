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


package org.pentaho.ui.xul.swt.tags;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulRadioGroup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.util.Orient;

public class SwtRadioGroup extends SwtBox implements XulRadioGroup {
  private ButtonGroup buttonGroup = new ButtonGroup();

  public SwtRadioGroup( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( self, parent, tagName, domContainer, Orient.VERTICAL );
  }

  @Override
  public void addChild( Element c ) {
    addComponentToButtonGroup( (XulComponent) c );
    super.addChild( c );
  }

  protected void addComponentToButtonGroup( XulComponent c ) {
    for ( XulComponent child : c.getChildNodes() ) {
      addComponentToButtonGroup( child );
    }
    if ( AbstractButton.class.isAssignableFrom( c.getManagedObject().getClass() ) ) {
      this.buttonGroup.add( (AbstractButton) c.getManagedObject() );
    }
  }

  public void resetContainer() {

  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.resetContainer();
    super.replaceChild( oldElement, newElement );
  }
}
