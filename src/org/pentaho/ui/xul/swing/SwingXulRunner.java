/**
 * 
 */
package org.pentaho.ui.xul.swing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.dom4j.Document;
import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulServiceCall;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.swing.tags.SwingWindow;

/**
 * @author OEM
 *
 */
public class SwingXulRunner implements XulRunner {

  private Document document;
  private JFrame rootFrame;
  private XulEventHandler eventHandler;
  private List<XulWindowContainer> containers; 
  
  public SwingXulRunner(){
    containers = new ArrayList<XulWindowContainer>();
  }
  
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#initialize()
   */
  public void initialize() throws XulException{
    //get first Element, should be a JFrame and show it.
    XulElement rootEle = containers.get(0).getDocumentRoot().getRootElement().getXulElement();

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

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#addContainer(org.pentaho.ui.xul.XulWindowContainer)
   */
  public void addContainer(XulWindowContainer xulWindowContainer) {
    this.containers.add(xulWindowContainer);
    
  }

  public List<XulWindowContainer> getXulWindowContainers() {
    return containers;
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    try{
      
      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("org/pentaho/ui/xul/sampleXul.xml");
      if(in == null){
        System.out.println("Input is null");
        System.exit(123);
      }
      
      Document doc = CleanXmlHelper.getDocFromStream(in);
      
      XulRunner runner = new SwingXulLoader().loadXul(doc);
      runner.initialize();
      runner.start();
      
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
    }
  }

}
