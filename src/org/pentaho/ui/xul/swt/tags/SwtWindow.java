package org.pentaho.ui.xul.swt.tags;

import java.awt.Dimension;
import java.lang.reflect.Method;

import org.dom4j.Document;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulWindow;

public class SwtWindow extends XulElement implements XulWindow {

  Shell shell;
  private XulEventHandler eventHandler;
  private int width;
  private int height;
  private Document rootDocument;
  private XulRunner xulRunner;
  
  public SwtWindow(XulElement parent, String tagName) {
    super(tagName);
    shell = (parent != null) ? 
                new Shell((Shell)parent.getManagedObject(), SWT.DIALOG_TRIM):
                new Shell(new Display(), SWT.DIALOG_TRIM);
    shell.setLayout(new RowLayout(SWT.VERTICAL));
    managedObject = shell;
  }

  public XulEventHandler getEventHandler() {
    return eventHandler;
  }

  public int getHeight() {
    return height;
  }
  
  public void setHeight(int height){
    this.height = height;
    shell.setSize(width, height);
  }

  public int getWidth() {
    return width;
  }
  
  public void setWidth(int width){
    this.width = width;
    shell.setSize(width, height);
  }

  public void invoke(String method, Object[] args) {
    try{
      method = method.replace("()", "");
      Method m = eventHandler.getClass().getMethod(method, new Class[0]);
      m.invoke(eventHandler, args);
      
    } catch(Exception e){
      // throw new XulException(e);
    }
  }

  public void setEventHandlerClass(String name) {
    try{
      Class cls = Class.forName(name);
      eventHandler = (XulEventHandler) cls.newInstance();
      eventHandler.setXulRunner(this.xulRunner);
      
    } catch(Exception e){
      // throw new XulException(e);
    }
  }

  public void setTitle(String title) {
    shell.getDisplay().setAppName(title);

  }

  public void setRootDocument(Document document){
    this.rootDocument = document;
  }
  
  public void setXulRunner(XulRunner xulRunner){
    this.xulRunner = xulRunner;
  }

  public void add(XulComponent component) {
    // intentionally does nothing

  }

}
