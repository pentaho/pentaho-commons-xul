/**
 * 
 */
package org.pentaho.ui.xul.swing;

import java.io.InputStream;

import javax.swing.JFrame;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulParser;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulServiceCall;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.swing.tags.SwingButtonHandler;
import org.pentaho.ui.xul.swing.tags.SwingHboxHandler;
import org.pentaho.ui.xul.swing.tags.SwingLabelHandler;
import org.pentaho.ui.xul.swing.tags.SwingWindowHandler;
import org.pentaho.ui.xul.swing.tags.SwingWindow;
import org.pentaho.ui.xul.swing.tags.SwingTextboxHandler;
import org.pentaho.ui.xul.swing.tags.SwingVboxHandler;

/**
 * @author OEM
 *
 */
public class SwingXulRunner implements XulRunner {

  private Document document;
  private JFrame rootFrame;
  private XulEventHandler eventHandler;
  
  public SwingXulRunner(){
  }
  
  public void setDocumentRoot(Document document){

    this.document = document;
    String className = "not initialized";
   
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#getDocumentRoot()
   */
  public Document getDocumentRoot() {
    // TODO Auto-generated method stub
    return document;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#getElementById(java.lang.String)
   */
  public XulComponent getElementById(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#getElementsById(java.lang.String)
   */
  public XulComponent getElementsById(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#initialize()
   */
  public void initialize() throws XulException{
    //get first Element, should be a JFrame and show it.
    XulElement rootEle = (XulElement) document.getRootElement();

    eventHandler = ((XulWindow) rootEle).getEventHandler();
    if(rootEle instanceof SwingWindow){
      rootFrame = (JFrame) rootEle.getManagedObject();
    } else {
      throw new XulException("Root element not a Frame");
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#remoteCall(org.pentaho.ui.xul.XulServiceCall)
   */
  public Document remoteCall(XulServiceCall serviceUrl) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#start()
   */
  public void start() {
    //rootFrame.pack();
    rootFrame.setVisible(true);

  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#stop()
   */
  public void stop() {
    // TODO Auto-generated method stub

  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    try{

      //register handlers, done with Spring in the future?
      XulParser.registerHandler("WINDOW", new SwingWindowHandler());
      XulParser.registerHandler("BUTTON", new SwingButtonHandler());
      XulParser.registerHandler("VBOX", new SwingVboxHandler());
      XulParser.registerHandler("HBOX", new SwingHboxHandler());
      XulParser.registerHandler("LABEL", new SwingLabelHandler());
      XulParser.registerHandler("TEXTBOX", new SwingTextboxHandler());
      
      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("org/pentaho/ui/xul/sampleXul.xml");
      if(in == null){
        System.out.println("Input is null");
        System.exit(123);
      }
      
      Document doc = CleanXmlHelper.getDocFromStream(in);
      
      SwingXulRunner runner = new SwingXulLoader().loadXul(doc);
      runner.initialize();
      runner.start();
      
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
    }
  }

}
