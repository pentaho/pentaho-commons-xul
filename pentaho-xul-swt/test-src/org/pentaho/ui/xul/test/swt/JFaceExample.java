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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.jface.tags.PentahoMenuManager;

public class JFaceExample extends ApplicationWindow implements Listener, ShellListener, DisposeListener {
  private ExitAction exitAction;
  Menu swtSubMenu;
  PentahoMenuManager subMenu;
  boolean running;
  boolean stopped = false;

  public JFaceExample() {
    super( null );
    exitAction = new ExitAction( this );
    this.addMenuBar();

  }

  public void menuInit() {
    subMenu.setEnabled( false );
  }

  protected Control createContents( Composite parent ) {
    getShell().setText( "JFace File Explorer" );
    // getShell().addDisposeListener(this);
    getShell().addShellListener( this );
    setStatus( "JFace Example v1.0" );
    menuInit();

    this.getShell().getDisplay().addListener( SWT.Close, this );

    return parent;
  }

  public void stop( boolean doClose ) {
    if ( stopped ) {
      return;
    }
    System.out.println( "stopping" );
    System.out.println( getShell() );
    if ( getShell() != null ) {
      if ( doClose ) {
        close();
      }
    }
    stopped = true;
  }

  public int open() {

    setBlockOnOpen( false );
    super.open();
    runEventLoop2( getShell() );
    return 0;
  }

  private void runEventLoop2( Shell loopShell ) {
    // Use the display provided by the shell if possible
    Display display;
    if ( getShell() == null ) {
      display = Display.getCurrent();
    } else {
      display = loopShell.getDisplay();
    }
    while ( loopShell != null && !loopShell.isDisposed() ) {
      try {
        if ( !display.readAndDispatch() ) {
          display.sleep();
        }
      } catch ( Throwable e ) {
      }
    }
    if ( !display.isDisposed() ) {
      display.update();
    }
  }

  protected MenuManager createMenuManager() {
    MenuManager menuBar = new MenuManager( "" );
    MenuManager fileMenu = new MenuManager( "&File" );
    menuBar.add( fileMenu );

    subMenu = new PentahoMenuManager( "sub1", "sub1" );
    fileMenu.add( subMenu );

    Action action1 = new Action( "menu item 1" ) {
      public void run() {
        System.out.println( "menu item 1" );
      }
    };
    subMenu.add( action1 );

    Action action2 = new Action( "menu item 1" ) {
      public void run() {
        System.out.println( "menu item 2" );
      }
    };
    action2.setChecked( true );
    fileMenu.add( action2 );

    fileMenu.add( exitAction );
    return menuBar;
  }

  public static void main( String[] args ) {
    JFaceExample fe = new JFaceExample();
    fe.open();
  }

  @Override
  public void handleEvent( Event arg0 ) {
    this.stop( false );
  }

  @Override
  public void shellActivated( ShellEvent arg0 ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void shellClosed( ShellEvent arg0 ) {

    System.out.println( "shellClosed()" );
    stop( false );
  }

  @Override
  public void shellDeactivated( ShellEvent arg0 ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void shellDeiconified( ShellEvent arg0 ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void shellIconified( ShellEvent arg0 ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void widgetDisposed( DisposeEvent arg0 ) {

    System.out.println( "widgetDisposed()" );
    stop( false );

  }

}
