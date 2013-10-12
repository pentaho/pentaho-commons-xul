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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

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

  public SwtTabpanel( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "tabpanel" );

    this.orient = Orient.VERTICAL;

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

}
