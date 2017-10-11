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
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenupopup extends AbstractSwingContainer implements XulMenupopup {

  public SwingMenupopup( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menupopup" );
  }

  public void layout() {
    initialized = true;
    final XulComponent component = getParent();
    if ( component instanceof SwingElement ) {
      final SwingElement parent = (SwingElement) component;
      parent.layout();
    }
  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    resetContainer();
    super.replaceChild( oldElement, newElement );
  }

  public void resetContainer() {
    super.resetContainer();
    final XulComponent component = getParent();
    if ( component instanceof SwingElement ) {
      final SwingElement parent = (SwingElement) component;
      parent.resetContainer();
    }
  }
}
