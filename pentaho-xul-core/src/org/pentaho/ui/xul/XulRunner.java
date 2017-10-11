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

import java.util.List;

/**
 * The XULRunner provides an encapsulated environment to run and manage a completely XUL based user interface.
 * 
 * @author nbaker
 * 
 */
public interface XulRunner {

  /**
   * Initialize any internal params, perform any pre-application tasks for the runner.
   * 
   * @throws XulException
   *           Exception thrown if the initialization fails for any reason.
   * 
   */
  public void initialize() throws XulException;

  /**
   * Start the application that this runner manages.
   * 
   * @throws XulException
   *           Exception thrown if the startup fails for any reason.
   * 
   */
  public void start() throws XulException;

  /**
   * Stop the application that this runner manages.
   * 
   * @throws XulException
   *           Exception thrown if the shutdown fails for any reason.
   * 
   */
  public void stop() throws XulException;

  /**
   * Add a new container to this runner. Each container represents a series of DOM models and their event handlers.
   * 
   * @param xulDomContainer
   *          The container to add to the runner.
   * 
   */
  public void addContainer( XulDomContainer xulDomContainer );

  /**
   * Returns the containers assigned to this runner.
   * 
   * @return a List of the assigned containers.
   * 
   */
  public List<XulDomContainer> getXulDomContainers();

}
