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

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class TestApplicationWindow extends ApplicationWindow {

  private ExitAction exitAction;

  public static void main( String[] args ) {
    new TestApplicationWindow().run();
  }

  public TestApplicationWindow() {
    super( null );
    // exitAction = new ExitAction( this );
    this.addMenuBar();
  }

  @Override
  public int open() {

    System.out.println( this.getMenuBarManager() );

    // populateMenuManager();

    return super.open();
  }

  protected MenuManager populateMenuManager() {
    MenuManager menuBar = this.getMenuBarManager();
    MenuManager fileMenu = new MenuManager( "&File" );
    MenuManager helpMenu = new MenuManager( "&Help" );
    menuBar.add( fileMenu );
    menuBar.add( helpMenu );
    fileMenu.add( exitAction );
    return menuBar;
  }

  public void run() {
    setBlockOnOpen( true );
    open();
    Display.getCurrent().dispose();
  }

  protected Control createContents( Composite parent ) {
    Label label = new Label( parent, SWT.CENTER );
    label.setText( "Hello, World" );
    return label;
  }
  /*
   * private class ExitAction extends Action { ApplicationWindow window;
   * 
   * public ExitAction(ApplicationWindow w) { window = w; setText("E&xit@Ctrl+W");
   * setToolTipText("Exit the application"); InputStream in = null; try { in =
   * this.getClass().getResourceAsStream("/foo.png"); Image img = new Image(Display.getCurrent(), in); ImageDescriptor
   * id = ImageDescriptor.createFromImage(img);
   * 
   * setImageDescriptor(id);
   * 
   * setImageDescriptor(id); } catch( Exception e ) { e.printStackTrace(); } finally { try{ if(in != null){ in.close();
   * } } catch(IOException ignored){} } }
   * 
   * public void run() { window.close(); } }
   */

}
