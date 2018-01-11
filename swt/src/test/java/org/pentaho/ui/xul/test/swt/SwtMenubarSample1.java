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
