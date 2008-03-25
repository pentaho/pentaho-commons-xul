package org.pentaho.ui.xul.swt.tags;

import java.lang.reflect.Method;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtWindow extends SwtElement implements XulWindow {
  private static final long serialVersionUID = 6711745093238802441L;

  Shell shell;

  private int width;

  private int height;

  private String onload;

  private XulDomContainer xulDomContainer;

  public SwtWindow(XulElement parent, XulDomContainer container, String tagName) {
    super(tagName);
    shell = (parent != null) ? new Shell((Shell) parent.getManagedObject(), SWT.SHELL_TRIM) : new Shell(SWT.SHELL_TRIM);
    shell.setLayout(new GridLayout());
    managedObject = shell;
    xulDomContainer = container;

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

      XulEventHandler evt = this.xulDomContainer.getEventHandler(eventID);
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

  public void setXulDomContainer(XulDomContainer xulDomContainer) {
    this.xulDomContainer = xulDomContainer;
  }

  public XulDomContainer getXulDomContainer() {
    return xulDomContainer;
  }

  public String getOnload() {
    return onload;
  }

  public void setOnload(final String method) {
    this.onload = method;
    shell.addListener(EVENT_ON_LOAD, new Listener() {
      public void handleEvent(Event e) {
        invoke(method, new Object[] {});
      }
    });

  }

  public void notifyListeners(int event) {
    if (!shell.isDisposed()) {
      shell.notifyListeners(event, new Event());
    }
  }

  public void open() {
    shell.open();

    while (!shell.isDisposed()) {
      if (!shell.getDisplay().readAndDispatch()) {
        shell.getDisplay().sleep();
      }
    }
  }

  public void close() {
    shell.dispose();
  }

}
