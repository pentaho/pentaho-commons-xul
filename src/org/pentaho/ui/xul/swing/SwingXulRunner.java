/**
 * 
 */
package org.pentaho.ui.xul.swing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.impl.XulServiceCall;
import org.pentaho.ui.xul.swing.tags.SwingWindow;
import org.pentaho.ui.xul.util.XulUtil;

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
    XulWindow rootEle = (XulWindow) containers.get(0).getDocumentRoot().getRootElement();
    
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
      
 
//      XulDomContainer container = new SwingXulLoader().loadXul(
//    		  "resource/documents/internationalization.xul", 
//    		  ResourceBundle.getBundle("resource/documents/internationalization2")
//      );

    	XulDomContainer container = new SwingXulLoader().loadXul(
      		  "resource/documents/pooling_table.xul"
        );
    	
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
