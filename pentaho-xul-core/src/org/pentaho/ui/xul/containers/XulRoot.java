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
