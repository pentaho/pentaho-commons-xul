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
