package org.pentaho.ui.xul.swt;

import java.io.InputStream;

import org.dom4j.Document;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulParser;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulServiceCall;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtWindow;


public class SwtXulRunner implements XulRunner {

  private Document document;

  private Composite rootFrame;

  private XulEventHandler eventHandler;

  public SwtXulRunner() {

    XulParser.registerHandler("WINDOW", "org.pentaho.ui.xul.swt.tags.SwtWindow");
    XulParser.registerHandler("BUTTON", "org.pentaho.ui.xul.swt.tags.SwtButton");
    XulParser.registerHandler("BOX", "org.pentaho.ui.xul.swt.tags.SwtBox");
    XulParser.registerHandler("VBOX", "org.pentaho.ui.xul.swt.tags.SwtVbox");
    XulParser.registerHandler("HBOX", "org.pentaho.ui.xul.swt.tags.SwtHbox");
    XulParser.registerHandler("LABEL", "org.pentaho.ui.xul.swt.tags.SwtLabel");
    XulParser.registerHandler("TEXTBOX", "org.pentaho.ui.xul.swt.tags.SwtTextbox");
    XulParser.registerHandler("GROUPBOX", "org.pentaho.ui.xul.swt.tags.SwtGroupbox");

  }

  public Document getDocumentRoot() {
    return this.document;
  }

  public XulComponent getElementById(String id) {
    return null;
  }

  public XulComponent getElementsById(String id) {
    return null;
  }

  public void initialize() throws XulException {

    // TODO Should initialize return a status? 

    if (document != null) {

      XulElement rootEle = (XulElement) document.getRootElement();
      eventHandler = ((XulWindow) rootEle).getEventHandler();
      if (rootEle instanceof SwtWindow) {
        rootFrame = (Composite) rootEle.getManagedObject();
      } else {
        throw new XulException("Unexpected root element: " + rootEle.getManagedObject().toString());
      }

    }
  }

  public Document remoteCall(XulServiceCall serviceUrl) {
    return null;
  }

  public void setDocumentRoot(Document document) {
    this.document = document;
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

  public static void main(String[] args) {

    try {
      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("resource/documents/samplexul.xul");
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
