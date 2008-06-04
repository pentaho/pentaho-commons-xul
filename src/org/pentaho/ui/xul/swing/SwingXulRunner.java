/**
 * 
 */
package org.pentaho.ui.xul.swing;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.swing.tags.SwingDialog;
import org.pentaho.ui.xul.swing.tags.SwingWindow;

/**
 * @author OEM
 *
 */
public class SwingXulRunner implements XulRunner {

  private Window rootFrame;
  private List<XulDomContainer> containers; 
  
  public SwingXulRunner(){
    containers = new ArrayList<XulDomContainer>();
  }
  
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulRunner#initialize()
   */
  public void initialize() throws XulException{
    //get first Element, should be a JFrame and show it.
    XulRoot rootEle = (XulRoot) containers.get(0).getDocumentRoot().getRootElement();
    
    //call the onLoads
    containers.get(0).initialize();
    
    if(rootEle instanceof SwingWindow){
      rootFrame = (JFrame) ((SwingWindow)rootEle).getManagedObject();
    } else if(rootEle instanceof SwingDialog){
      rootFrame = ((SwingDialog)rootEle).getDialog();
    } else {
      throw new XulException("Root element not a Frame");
    }
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

}
