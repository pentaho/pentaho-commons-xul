package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtWindow extends SwtElement implements XulWindow {
  private static final long serialVersionUID = 6711745093238802441L;

  Shell shell;

  private int width;

  private int height;

  private String onload;

  private XulDomContainer xulDomContainer;
  
  private String title; 
  
  private static final Log logger = LogFactory.getLog(SwtWindow.class);

  public SwtWindow(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    shell = (parent != null) ? new Shell((Shell) parent.getManagedObject(), SWT.DIALOG_TRIM) : new Shell(SWT.SHELL_TRIM);
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

  public void setTitle(String title) {
    this.title = title;
    Display.setAppName(title);
    shell.setText(title);

  }

  public String getTitle() {
    // TODO Auto-generated method stub
    return title;
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
    shell.addListener(XulRoot.EVENT_ON_LOAD, new Listener() {
      public void handleEvent(Event e) {
        invoke(method);
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
  
  public boolean isClosed(){
    return shell.isDisposed();
  }

	public void copy() throws XulException {
	  throw new UnsupportedOperationException();
	}

	public void cut() throws XulException {
	  throw new UnsupportedOperationException();
	}

	public void paste() throws XulException {
	  throw new UnsupportedOperationException();		
	}

  public void copy(String content) throws XulException {
    throw new UnsupportedOperationException();
  }

}
