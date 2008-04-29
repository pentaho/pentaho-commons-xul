package org.pentaho.ui.xul.swt;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.impl.XulServiceCall;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtWindow;


public class SwtXulRunner implements XulRunner {

  private Composite rootFrame;
  private List<XulDomContainer> containers; 

  public SwtXulRunner() {
    containers = new ArrayList<XulDomContainer>();
  }

  public XulComponent getElementById(String id) {
    return null;
  }

  public XulComponent getElementsById(String id) {
    return null;
  }

  public void initialize() throws XulException {

    // TODO Should initialize return a status? Reply: Just let them catch the Exception

    XulComponent rootEle = containers.get(0).getDocumentRoot().getRootElement();

    if (rootEle instanceof SwtWindow) {
      rootFrame = (Composite) rootEle.getManagedObject();
    } else {
      throw new XulException("Unexpected root element: " + rootEle.getManagedObject().toString());
    }

  }

  public Document remoteCall(XulServiceCall serviceUrl) {
    return null;
  }


  public void start() throws XulException {
    ((Shell)rootFrame).open(); 
    while(!rootFrame.isDisposed()) {
      // process the next event, wait when none available
      if(!rootFrame.getDisplay().readAndDispatch()) {
        rootFrame.getDisplay().sleep();
      }
   }
  }

  public void stop() throws XulException {
    if(((Shell)rootFrame).isDisposed()){
      return;
    }
    ((Shell)rootFrame).dispose();
  }
  
  public void addContainer(XulDomContainer xulDomContainer) {
    this.containers.add(xulDomContainer);
  }
  

  public List<XulDomContainer> getXulDomContainers() {
    return containers;
  }

  public static void main(String[] args) {

    try {
  

      XulDomContainer container = new SwtXulLoader().loadXul(
      		  "resource/documents/internationalization.xul"
      );
      
      XulRunner runner = new SwtXulRunner();
      runner.addContainer(container);
      
      runner.initialize();


      runner.start();


    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
    }

  }

}
