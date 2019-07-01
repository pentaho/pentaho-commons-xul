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
