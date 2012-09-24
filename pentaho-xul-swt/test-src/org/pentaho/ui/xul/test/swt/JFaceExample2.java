package org.pentaho.ui.xul.test.swt;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

/**
 * Your first JFace application
 */
public class JFaceExample2 extends ApplicationWindow {
  /**
   * HelloWorld constructor
   */
  public JFaceExample2() {
    super(null);
  }

  @Override 
  protected boolean canHandleShellCloseEvent() {
	  return true;
  }
  
  @Override
  protected void handleShellCloseEvent() {
	  System.out.println("handleShellCloseEvent");
  }
  
  /**
   * Runs the application
   */
  public void run() {
    // Don't return from open() until window closes
    setBlockOnOpen(true);

    // Open the main window
    open();

	  System.out.println(getShell());
    
    // Dispose the display
    if( Display.getCurrent() != null && !Display.getCurrent().isDisposed() ) {
    	Display.getCurrent().dispose();
    }
  }

  /**
   * Creates the main window's contents
   * 
   * @param parent the main window
   * @return Control
   */
  protected Control createContents(Composite parent) {
    // Create a Hello, World label
    Label label = new Label(parent, SWT.CENTER);
    label.setText("Hello, World");
    return label;
  }

  /**
   * The application entry point
   * 
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    new JFaceExample2().run();
  }
}
