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

package org.pentaho.ui.xul.test.swt;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Your first JFace application
 */
public class JFaceExample2 extends ApplicationWindow {
  /**
   * HelloWorld constructor
   */
  public JFaceExample2() {
    super( null );
  }

  @Override
  protected boolean canHandleShellCloseEvent() {
    return true;
  }

  @Override
  protected void handleShellCloseEvent() {
    System.out.println( "handleShellCloseEvent" );
  }

  /**
   * Runs the application
   */
  public void run() {
    // Don't return from open() until window closes
    setBlockOnOpen( true );

    // Open the main window
    open();

    System.out.println( getShell() );

    // Dispose the display
    if ( Display.getCurrent() != null && !Display.getCurrent().isDisposed() ) {
      Display.getCurrent().dispose();
    }
  }

  /**
   * Creates the main window's contents
   * 
   * @param parent
   *          the main window
   * @return Control
   */
  protected Control createContents( Composite parent ) {
    // Create a Hello, World label
    Label label = new Label( parent, SWT.CENTER );
    label.setText( "Hello, World" );
    return label;
  }

  /**
   * The application entry point
   * 
   * @param args
   *          the command line arguments
   */
  public static void main( String[] args ) {
    new JFaceExample2().run();
  }
}
