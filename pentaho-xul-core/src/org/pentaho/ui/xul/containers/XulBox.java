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
 * An interface for a box container. This container will add its children according to the orientation set. The
 * default is horizontally.
 * 
 * @author nbaker
 */

public interface XulBox extends XulContainer {

  /**
   * Sets the orientation of this box container, which dictates in what direction the box's child controls get
   * added.
   * 
   * @param orient
   *          Valid values are horizontal or vertical
   * @see org.pentaho.ui.xul.util.Orient
   */
  public void setOrient( String orient );

  /**
   * 
   * @return the box's orientation setting, as a string.
   */
  public String getOrient();

}
