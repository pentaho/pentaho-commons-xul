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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.Orient;

public class SwtTabpanel extends AbstractSwtXulContainer implements XulTabpanel {

  private Composite panel;
  private String style;

  public SwtTabpanel( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "tabpanel" );

    this.orient = Orient.VERTICAL;

    this.style = self.getAttributeValue( "style" );

    if ( parent instanceof SwtTabpanels ) {
      panel = new Composite( (Composite) ( (SwtTabpanels) parent ).getTabbox().getManagedObject(), SWT.NONE );
    } else {
      panel = new Composite( (Composite) parent.getManagedObject(), SWT.NONE );
    }
    setManagedObject( panel );

  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    super.replaceChild( oldElement, newElement );
  }

  public String getStyle() {
    return style;
  }
}
