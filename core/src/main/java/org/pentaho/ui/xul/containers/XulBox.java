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
