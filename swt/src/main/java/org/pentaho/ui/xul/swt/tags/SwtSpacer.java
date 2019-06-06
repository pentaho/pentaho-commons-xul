/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2019 Hitachi Vantara..  All rights reserved.
 */

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
