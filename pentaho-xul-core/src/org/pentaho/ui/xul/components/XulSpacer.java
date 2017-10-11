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

/**
 * 
 */

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * An interface for a XUL spacer widget. A spacer is an element that takes up space but does not display anything.
 * It is usually used to place spacing within a container. If you don't specify that the spacer has a size or is
 * flexible, the spacer does not occupy any space.
 * 
 * @author nbaker
 * 
 */
public interface XulSpacer extends XulComponent {

  /**
   * Set the height that this space should occupy.
   * 
   * @param size
   *          The hieght.
   */
  public void setHeight( int size );

  /**
   * Set the width that this space should occupy.
   * 
   * @param size
   *          The width.
   */
  public void setWidth( int size );

}
