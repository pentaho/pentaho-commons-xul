package org.pentaho.ui.xul.swt;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulRoot;
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
    
    //call the onLoads
    containers.get(0).initialize();

    if (rootEle instanceof SwtWindow) {
      rootFrame = (Composite) rootEle.getManagedObject();
    } else {
      throw new XulException("Unexpected root element: " + rootEle.getManagedObject().toString());
    }

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


}
