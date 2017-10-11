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

package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulDomContainer;

public interface XulEventHandler {

  /**
   * The name of this event handler instance; maps to event handler literals in the xul document.
   * 
   * @return The name of this event handler instance.
   */
  public String getName();

  public void setName( String name );

  public void setXulDomContainer( XulDomContainer xulDomContainer );

  public XulDomContainer getXulDomContainer();

  /**
   * A generic way of returning data from event handlers... can we do better than this? Handle return values from
   * invoked methods? possibly?
   * 
   * @return any data associated with events that have been executed.
   */
  public Object getData();

  /**
   * A generic way of passing data to the event handler. It seems we should maybe accept parameters instead of
   * doing this.
   * 
   * @param any
   *          data events may want to operate on.
   */
  public void setData( Object data );

}
