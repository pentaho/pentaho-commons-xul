package org.pentaho.ui.xul.swt;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulServiceCall;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtWindow;


public class SwtXulRunner implements XulRunner {

  private Composite rootFrame;
  private List<XulWindowContainer> containers; 

  public SwtXulRunner() {
    containers = new ArrayList<XulWindowContainer>();
  }

  public XulComponent getElementById(String id) {
    return null;
  }

  public XulComponent getElementsById(String id) {
    return null;
  }

  public void initialize() throws XulException {

    // TODO Should initialize return a status? Reply: Just let them catch the Exception

    XulElement rootEle = (XulElement) containers.get(0).getDocumentRoot().getRootElement();

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
  
  public void addContainer(XulWindowContainer xulWindowContainer) {
    this.containers.add(xulWindowContainer);
  }
  

  public List<XulWindowContainer> getXulWindowContainers() {
    return containers;
  }

  public static void main(String[] args) {

    try {
      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("resource/documents/sampleXul.xml");
      if (in == null) {
        System.out.println("Input is null");
        System.exit(123);
      }

      Document doc = CleanXmlHelper.getDocFromStream(in);

      XulRunner runner = new SwtXulLoader().loadXul(doc);

      runner.initialize();
      runner.start();

    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
    }

  }

}
