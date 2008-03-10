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
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.containers.XulWindow;

public class SwtWindow extends XulElement implements XulWindow {

  Shell shell;
  private XulEventHandler eventHandler;
  private int width;
  private int height;
  private Document rootDocument;
  private XulWindowContainer xulWindowContainer;
  
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
      if(method.indexOf('.') == -1){
        throw new IllegalArgumentException("method call does not follow the pattern [EventHandlerID].methodName()");
      }
      
      method = method.replace("()", "");
      String[] pair = method.split("\\.");
      String eventID = pair[0];
      String methodName = pair[1];
      
      XulEventHandler eventHandler = this.xulWindowContainer.getEventHandler(eventID);
      Method m = eventHandler.getClass().getMethod(methodName, new Class[0]);
      m.invoke(eventHandler, args);
      
    } catch(Exception e){
      System.out.println("Error invoking method: "+method);
      e.printStackTrace(System.out);
    }
  }

  

  public void setTitle(String title) {
    shell.getDisplay().setAppName(title);

  }

  public void setRootDocument(Document document){
    this.rootDocument = document;
  }
  
  public void add(XulComponent component) {
    // intentionally does nothing

  }
  
  public void setXulWindowContainer(XulWindowContainer xulWindowContainer){
    this.xulWindowContainer = xulWindowContainer;
  }

  public XulWindowContainer getXulWindowContainer(XulWindowContainer xulWindowContainer) {
    // TODO Auto-generated method stub
    return xulWindowContainer;
  }

}
