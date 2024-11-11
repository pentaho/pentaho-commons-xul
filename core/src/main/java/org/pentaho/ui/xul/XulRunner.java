/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


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
