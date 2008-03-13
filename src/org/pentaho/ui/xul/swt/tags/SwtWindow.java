package org.pentaho.ui.xul.swt.tags;

import java.lang.reflect.Method;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtWindow extends SwtElement implements XulWindow {
  private static final long serialVersionUID = 6711745093238802441L;

  Shell shell;

  private XulEventHandler eventHandler;

  private int width;
  private int height;

  private XulWindowContainer xulWindowContainer;

  public SwtWindow(XulElement parent, XulWindowContainer container, String tagName) {
    super(tagName);
    shell = (parent != null) ? new Shell((Shell) parent.getManagedObject(), SWT.SHELL_TRIM) : 
      new Shell(new Display(),SWT.SHELL_TRIM);
    shell.setLayout(new GridLayout());
    managedObject = shell;
    xulWindowContainer = container;
  }

  public XulEventHandler getEventHandler() {
    return eventHandler;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
    shell.setSize(width, height);
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
    shell.setSize(width, height);
  }

  public void invoke(String method, Object[] args) {

    try {
      if (method.indexOf('.') == -1) {
        throw new IllegalArgumentException("method call does not follow the pattern [EventHandlerID].methodName()");
      }

      method = method.replace("()", "");
      String[] pair = method.split("\\.");
      String eventID = pair[0];
      String methodName = pair[1];

      XulEventHandler evt = this.xulWindowContainer.getEventHandler(eventID);
      Method m = evt.getClass().getMethod(methodName, new Class[0]);
      m.invoke(evt, args);

    } catch (Exception e) {
      System.out.println("Error invoking method: " + method);
      e.printStackTrace(System.out);
    }
  }

  public void setTitle(String title) {
    Display.setAppName(title);

  }

  public void addComponent(XulComponent component) {
    // intentionally does nothing

  }

  public void setXulWindowContainer(XulWindowContainer xulWindowContainer) {
    this.xulWindowContainer = xulWindowContainer;
  }

  public XulWindowContainer getXulWindowContainer() {
    return xulWindowContainer;
  }

}
