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

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSpacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

/**
 * @author nbaker
 * 
 */
public class SwtSpacer extends SwtElement implements XulSpacer {
  private Label strut;

  public SwtSpacer( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "spacer" );
    strut = new Label( (Composite) parent.getManagedObject(), SWT.NONE );

    String attrHeight = self.getAttributeValue( "height" );
    int height = StringUtils.isEmpty( attrHeight ) ? 5 : Integer.parseInt( attrHeight );
    String attrWidth = self.getAttributeValue( "width" );
    int width = StringUtils.isEmpty( attrWidth ) ? 5 : Integer.parseInt( attrWidth );
    FormData fdSpacer = new FormData();
    fdSpacer.left = new FormAttachment( 0, 0 );
    fdSpacer.right = new FormAttachment( 0, width );
    fdSpacer.top = new FormAttachment( 0, 0 );
    fdSpacer.bottom = new FormAttachment( 0, height );
    strut.setLayoutData( fdSpacer );
    setManagedObject( strut );
  }
}
