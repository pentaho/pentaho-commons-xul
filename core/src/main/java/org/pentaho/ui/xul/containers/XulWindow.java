/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


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
