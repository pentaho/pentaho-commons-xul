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
 * An interface for a deck container. This container will stack its children one on top of the other, with only the
 * selected child showing. The selected index determines which child is displayed. The first child is selected by
 * default.
 * 
 * @author gmoran
 */

public interface XulDeck extends XulContainer {

  /**
   * The selected index is a zero based index referring to the child control of this deck that should be displayed.
   * 
   * @param index
   *          The index of the child to display.
   */
  public void setSelectedIndex( int index );

  /**
   * 
   * @return the index of the selected or displayed child control.
   */
  public int getSelectedIndex();

}
