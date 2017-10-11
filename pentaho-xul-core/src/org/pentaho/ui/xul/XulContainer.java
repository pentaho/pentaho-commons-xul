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

package org.pentaho.ui.xul;

import org.pentaho.ui.xul.util.Orient;

/**
 * The XulContainer interface represents those controls or widgets that can contain other controls or widgets.
 * 
 * @author nbaker
 */
public interface XulContainer extends XulComponent {

  /**
   * @Depreciated use addChild instead
   * 
   *              The method used to add child controls\widgets to a container.
   * @param component
   *          the child component to add to this container.
   */
  public void addComponent( XulComponent component );

  /**
   * @Depreciated use addChildAt instead
   * 
   *              The method used to add child controls\widgets to a container at the specified index
   * @param component
   *          the child component to add to this container.
   * @param index
   *          to add the component at (zero-based)
   */
  public void addComponentAt( XulComponent component, int idx );

  /**
   * @Depreciated use removeChild instead
   * 
   *              The method used to remove a child from a container
   * @param component
   *          the child component to remove from this container.
   */
  public void removeComponent( XulComponent component );

  /**
   * @see org.pentaho.ui.xul.util.Orient
   * @return the orientation for this container. Valid values are found in the Orient enum.
   */
  public Orient getOrientation();

  /**
   * Defeats layout calls. Useful for bulk updates.
   * 
   * @param suppress
   */
  public void suppressLayout( boolean suppress );

}
