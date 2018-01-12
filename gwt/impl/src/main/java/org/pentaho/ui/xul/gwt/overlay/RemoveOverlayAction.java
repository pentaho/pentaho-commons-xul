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

package org.pentaho.ui.xul.gwt.overlay;

import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;

/**
 * User: nbaker Date: 10/12/11
 */
public class RemoveOverlayAction implements IOverlayAction {

  private Element element;
  private Element parent;
  private int oldPos;

  public RemoveOverlayAction( Element element ) {
    this.element = element;
    this.parent = element.getParent();
  }

  public void perform() {
    oldPos = parent.getChildNodes().indexOf( element );
    parent.removeChild( element );
    ( (AbstractGwtXulComponent) parent ).layout();
  }

  public void remove() {
    parent.addChildAt( element, oldPos );
    ( (AbstractGwtXulComponent) parent ).layout();
  }
}
