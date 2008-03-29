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
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulServiceCall;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.swing.tags.SwingWindow;

/**
 * @author OEM
 *
 */
public class SwingXulRunner implements XulRunner {

  private JFrame rootFrame;
  private List<XulDomContainer> containers; 
  
  public SwingXulRunner(){
    containers = new ArrayList<XulDomContainer>();
  }
  
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#initialize()
   */
  public void initialize() throws XulException{
    //get first Element, should be a JFrame and show it.
    XulWindow rootEle = (XulWindow) containers.get(0).getDocumentRoot().getRootElement().getXulElement();
    
    //call the onLoads
    containers.get(0).initialize();
    
    if(rootEle instanceof SwingWindow){
      rootFrame = (JFrame) ((SwingWindow)rootEle).getManagedObject();
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
  public void addContainer(XulDomContainer xulDomContainer) {
    this.containers.add(xulDomContainer);
    
  }

  public List<XulDomContainer> getXulDomContainers() {
    return containers;
  }

  public static void main(String[] args) {
    try{
      
      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("org/pentaho/ui/xul/sampleXul.xul");
      //InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("org/pentaho/ui/xul/samples/datasource.xul");

      if(in == null){
        System.out.println("Input is null");
        System.exit(123);
      }
      
      Document doc = CleanXmlHelper.getDocFromStream(in);
      
      XulDomContainer container = new SwingXulLoader().loadXul(doc);

      XulRunner runner = new SwingXulRunner();
      runner.addContainer(container);
      
      runner.initialize();
      runner.start();
      
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
    }
  }

}
