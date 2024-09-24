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

package org.pentaho.ui.xul.jface.tags;

import org.eclipse.jface.window.ApplicationWindow;

public class ApplicationWindowLocal {

  private static final ThreadLocal<ApplicationWindow> threadLocal = new ThreadLocal<ApplicationWindow>();

  public static void setApplicationWindow( ApplicationWindow window ) {
    threadLocal.set( window );
  }

  public static ApplicationWindow getApplicationWindow() {
    return threadLocal.get();
  }

}
