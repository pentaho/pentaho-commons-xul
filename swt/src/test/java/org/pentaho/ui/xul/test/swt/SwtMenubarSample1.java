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

package org.pentaho.ui.xul.test.swt;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulException;

public class SwtMenubarSample1 extends ApplicationWindow {

  public SwtMenubarSample1( Shell parentShell ) {
    super( parentShell );

    this.addMenuBar();

    MenuManager menuBar = getMenuBarManager();

    MenuManager fileMenu = new MenuManager( "File", "FILE" );
    menuBar.add( fileMenu );

    Action action1 = new Action( "option1" ) {
    };
    fileMenu.add( action1 );

    MenuManager subMenu = new MenuManager( "submenu", "SUBMENU" );
    fileMenu.add( subMenu );

    Action action2 = new Action( "option2" ) {
    };
    subMenu.add( action2 );

    Action action3 = new Action( "option3" ) {
    };
    menuBar.add( action3 );

  }

  public static void main( String[] args ) throws IllegalArgumentException, XulException {

    SwtMenubarSample1 window = new SwtMenubarSample1( null );
    window.setBlockOnOpen( true );
    window.open();
    Display.getCurrent().dispose();
  }

}
