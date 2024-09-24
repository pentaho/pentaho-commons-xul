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

package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSeparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtSeparator extends SwtElement implements XulSeparator {
  public SwtSeparator( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "separator" );

    String orient = self.getAttributeValue( "orient" );
    orient = orient == null || orient.isEmpty() ? Orient.HORIZONTAL.toString() : orient;
    this.setOrient( orient );

    int dir = ( getOrientation() == Orient.VERTICAL ) ? SWT.VERTICAL : SWT.HORIZONTAL;
    Label wline = new Label( (Composite) parent.getManagedObject(), SWT.SEPARATOR | dir | SWT.CENTER );
    FormData fdLine = new FormData();
    fdLine.left = new FormAttachment( 0, 0 );
    fdLine.right = new FormAttachment( 100, 0 );
    fdLine.height = getHeight();
    wline.setLayoutData( fdLine );
    setManagedObject( wline );
  }

}
