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

package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulException;

/**
 * Typically the top level container, the XulWindow interface provides the necessary methods to display and dispose
 * of an independent container. Models the XUL window widget.
 * 
 * @author nbaker
 * 
 */
public interface XulWindow extends XulRoot {

  /**
   * Open the window for display.
   */
  public void open();

  /**
   * Close the window, and return control to the executing program.
   */
  public void close();

  /**
   * 
   * @return
   */
  public boolean isClosed();

  /**
   * 
   * @throws XulException
   */
  public void paste() throws XulException;

  /**
   * 
   * @throws XulException
   */
  public void copy() throws XulException;

  /**
   * 
   * @param content
   * @throws XulException
   */
  public void copy( String content ) throws XulException;

  /**
   * 
   * @throws XulException
   */
  public void cut() throws XulException;

}
