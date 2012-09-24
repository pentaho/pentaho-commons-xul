package org.pentaho.ui.xul.test.swt;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulException;

public class SwtMenubarTest extends ApplicationWindow {

  public SwtMenubarTest(Shell parentShell) {
		super(parentShell);

		this.addMenuBar();

	      MenuManager menuBar = getMenuBarManager();
	      
	      MenuManager fileMenu = new MenuManager("File", "FILE");
	      menuBar.add(fileMenu);

	      Action action1 = new Action("option1") {};
	      fileMenu.add(action1);
	      
	      MenuManager subMenu = new MenuManager("submenu", "SUBMENU");
	      fileMenu.add(subMenu);

	      Action action2 = new Action("option2") {};
	      subMenu.add(action2);
	      
	      Action action3 = new Action("option3") {};
	      menuBar.add(action3);
	      
	      
  }

  public static void main( String args[] ) throws IllegalArgumentException, XulException {
	  
	  SwtMenubarTest window = new SwtMenubarTest(null);
	  window.setBlockOnOpen( true );
	  window.open();
	    Display.getCurrent().dispose();	    
  }
  



}

  