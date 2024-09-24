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

package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;

public interface XulRoot extends XulContainer {

  /**
   * This is the event that gets fired once the XUL parser and loader have completed their work.
   */
  public static final int EVENT_ON_LOAD = 555;

  /**
   * Sets the title of the application window.
   * 
   * @param title
   *          The application's title text.
   */
  public void setTitle( String title );

  /**
   * 
   * @return the title
   */
  public String getTitle();

  public void setAppicon( String icon );

  /**
   * Creates a reference to the DOM container that will be managing this window and its events.
   * 
   * @param xulDomContainer
   *          the container holding this document.
   */
  public void setXulDomContainer( XulDomContainer xulDomContainer );

  /**
   * 
   * @return the DOM container managing this document.
   */
  public XulDomContainer getXulDomContainer();

  /**
   * Sets the method name to invoke during the onload event for this window.
   * 
   * @param onload
   *          The method name, in the form of [handlerId.medthodName()].
   */
  public void setOnload( String onload );

  /**
   * 
   * @return The method string used for the onload event.
   */
  public String getOnload();

  /**
   * Sets the method name to invoke during the onclose event for this window.
   * 
   * @param onclose
   *          The method name, in the form of [handlerId.medthodName()].
   */
  public void setOnclose( String onclose );

  /**
   * 
   * @return The method string used for the onclose event.
   */
  public String getOnclose();

  /**
   * Sets the method name to invoke during the onunload event for this window.
   * 
   * @param onunload
   *          The method name, in the form of [handlerId.medthodName()].
   */
  public void setOnunload( String onunload );

  /**
   * 
   * @return The method string used for the onunload event.
   */
  public String getOnunload();

  public Object getRootObject();

  public void invokeLater( Runnable runnable );
}
