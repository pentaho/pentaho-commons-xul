package org.pentaho.ui.xul.jface.tags;

import org.eclipse.jface.window.ApplicationWindow;

public class ApplicationWindowLocal {

	private static final ThreadLocal<ApplicationWindow> threadLocal = new ThreadLocal<ApplicationWindow>();
	
	public static void setApplicationWindow( ApplicationWindow window ) {
		threadLocal.set(window);
	}
	
	public static ApplicationWindow getApplicationWindow() {
		return threadLocal.get();
	}
	
}
