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
