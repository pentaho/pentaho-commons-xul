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

package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

/**
 * An interface for the XUL container groupbox. A groupbox behaves much like the XulBox, except that a groupbox has
 * a border drawn around its edge, and optionally can display a title at the top, using a XulCaption widget.
 * 
 * Unlike a XulBox, the groupbox defaults it orientation to vertical instead of horizontal.
 * 
 * @author nbaker
 * 
 */
public interface XulGroupbox extends XulContainer, XulCaptionedPanel {

  /**
   * Set the title on the top of the groupbox (optional).
   * 
   * @param caption
   *          The groupbox's title text.
   */
  public void setCaption( String caption );
}
