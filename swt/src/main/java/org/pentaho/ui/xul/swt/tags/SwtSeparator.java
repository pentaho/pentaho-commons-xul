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
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

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
