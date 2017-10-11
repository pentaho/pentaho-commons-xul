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

/**
 * The Xul wrapper for identifying XulEventHandler classes. This class merely associates a class name with an id in
 * a given window. The hooking up of events to these classes occurs at the control level. Windows can have as many
 * event handling classes as needed.
 * 
 * @author nbaker
 * 
 */
public interface XulScript {

  /**
   * The name of the class (or script) to use for event handling in this window.
   * 
   * @param src
   *          String name of class or script
   */
  public void setSrc( String src );

  /**
   * 
   * @return the class or script String identifier
   */
  public String getSrc();

  /**
   * The id to associate this class with. This id is later appended to the beginning of each event hookup method
   * name, in order to reference back to the event handler class that that method resides in.
   * 
   * @param id
   *          Id of this event handler class.
   */
  public void setId( String id );

  /**
   * 
   * @return The id of this class or script.
   */
  public String getId();
}
