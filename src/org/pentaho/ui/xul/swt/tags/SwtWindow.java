package org.pentaho.ui.xul.swt.tags;

import java.lang.reflect.Method;

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
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.impl.XulEventHandler;
import org.pentaho.ui.xul.swing.tags.SwingWindow;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtWindow extends SwtElement implements XulWindow {
  private static final long serialVersionUID = 6711745093238802441L;

  Shell shell;

  private int width;

  private int height;

  private String onload;

  private XulDomContainer xulDomContainer;
  
  private static final Log logger = LogFactory.getLog(SwtWindow.class);

  public SwtWindow(XulComponent parent, XulDomContainer container, String tagName) {
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

  public Object[] getArgs(String methodCall){
  	if(methodCall.indexOf("()") > -1){
  		return null;
  	}
  	String argsList = methodCall.substring(methodCall.indexOf("(")+1, methodCall.indexOf(")"));
  	String[] stringArgs = argsList.split(",");
  	Object[] args = new Object[ stringArgs.length ];
  	int i=-1;
  	for(String obj : stringArgs){
  		i++;
  		obj = obj.trim();
  		try{
  			Integer num = Integer.valueOf(obj);
  			args[i] = num;
  			continue;
  		} catch(NumberFormatException e){
  			try{
    			Double num = Double.valueOf(obj);
    			args[i] = num;
    			continue;
    		} catch(NumberFormatException e2){
    			try{
      			String str = obj.replaceAll("'", "");
      			str = str.replaceAll("\"", "");
      			args[i] = str;
      			continue;
      		} catch(NumberFormatException e3){
      			logger.error("Error parsing event call argument: "+obj, e3);
      			continue;
      		}
    		}
  		}
  	}
  	return args;
  	
  }
  
  public void invoke(String method, Object[] args) {

  	try {
      if (method.indexOf('.') == -1) {
        throw new IllegalArgumentException("method call does not follow the pattern [EventHandlerID].methodName()");
      }

      
      String[] pair = method.split("\\.");
      String eventID = method.substring(0, method.indexOf("."));
      String methodName = method.substring(method.indexOf(".")+1);
      
      Object[] arguments = getArgs(methodName);
      if(arguments != null){
      	invoke(method.substring(0,method.indexOf("("))+"()", arguments);
      	return;
      } else {
      	methodName = methodName.substring(0,methodName.indexOf("("));
      }
      
      XulEventHandler evt = this.xulDomContainer.getEventHandler(eventID);
      if(args.length > 0){
        Class[] classes = new Class[args.length];
        
        for(int i=0; i<args.length; i++){
          classes[i] = args[i].getClass();
        }
        
        Method m = evt.getClass().getMethod(methodName, classes);
        m.invoke(evt, args);
      } else {
        Method m = evt.getClass().getMethod(methodName, new Class[0]);
        m.invoke(evt, args);
      }
    } catch (Exception e) {
      logger.error("Error invoking method: " + method, e);
    }
  }

  public void setTitle(String title) {
    Display.setAppName(title);
    shell.setText(title);

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
  
  public boolean isClosed(){
    return shell.isDisposed();
  }

	public void copy() throws XulException {
		// TODO Auto-generated method stub
		
	}

	public void cut() throws XulException {
		// TODO Auto-generated method stub
		
	}

	public void paste() throws XulException {
		// TODO Auto-generated method stub
		
	}

}
