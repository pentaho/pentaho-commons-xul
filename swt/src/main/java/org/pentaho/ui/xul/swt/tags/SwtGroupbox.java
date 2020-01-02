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
 * Copyright (c) 2002-2020 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.util.Orient;

public class SwtGroupbox extends SwtBox implements XulGroupbox {
  private static final long serialVersionUID = 7414282626289178745L;

  SwtLabel label;

  public SwtGroupbox( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    // XUL groupbox defaults to VERTICAL orientation
    super( self, parent, tagName, container, Orient.VERTICAL );

    // self is not needed for this SwtLabel instance
    label = new SwtLabel( null, this, container, SwtLabel.TAG_NAME );
    this.addChild( label );
  }

  @Override
  protected Composite createNewComposite( Composite c ) {
    return new Group( c, SWT.NONE );
  }

  public void setCaption( String caption ) {
    if ( label != null ) {
      label.setValue( caption );
    }
  }

}
