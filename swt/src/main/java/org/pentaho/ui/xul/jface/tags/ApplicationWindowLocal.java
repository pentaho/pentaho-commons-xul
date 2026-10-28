/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
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
