/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.impl.XulEventHandler;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 *
 */
public class SwingWindow extends SwingElement implements XulWindow {

  JFrame frame;

  private int width;

  private int height;

  private Document rootDocument;

  private XulDomContainer xulDomContainer;

  private String onload;

  public SwingWindow(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("window");
    frame = new JFrame();
    this.xulDomContainer = domContainer;

    this.orientation = Orient.VERTICAL;

    children = new ArrayList<XulComponent>();

    container = new JPanel(new GridBagLayout());
    //container.setBorder(BorderFactory.createLineBorder(Color.green));
    managedObject = container;

    resetContainer();

    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(container, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    managedObject = frame;
  }


  public void resetContainer(){
    
    container.removeAll();
    
    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets(2, 2, 2, 2);
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;
    
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulPage#setTitle(java.lang.String)
   */
  public void setTitle(String title) {
    frame.setTitle(title);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulPage#getHeight()
   */
  public int getHeight() {
    // TODO Auto-generated method stub
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
    frame.setSize(new Dimension(this.width, this.height));
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulPage#getWidth()
   */
  public int getWidth() {
    // TODO Auto-generated method stub
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
    frame.setSize(new Dimension(this.width, this.height));
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulWindow#invoke(java.lang.String, java.lang.Object[])
   */
  public void invoke(String method, Object[] args) {

	try {
      if (method.indexOf('.') == -1) {
        throw new IllegalArgumentException("method call does not follow the pattern [EventHandlerID].methodName()");
      }

      method = method.replace("()", "");
      String[] pair = method.split("\\.");
      String eventID = pair[0];
      String methodName = pair[1];

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
      System.out.println("Error invoking method: " + method);
      e.printStackTrace(System.out);
    }
  }

  public void setRootDocument(Document document) {
    this.rootDocument = document;
  }

  public void setXulDomContainer(XulDomContainer xulDomContainer) {
    this.xulDomContainer = xulDomContainer;
  }

  public XulDomContainer getXulDomContainer() {
    return xulDomContainer;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulWindow#getOnload(java.lang.String)
   */
  public String getOnload() {
    return onload;

  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulWindow#setOnload(java.lang.String)
   */
  public void setOnload(String onload) {
    this.onload = onload;

  }

  @Override
  public void layout() {
    super.layout();
    for (XulComponent comp : children) {
      if (comp instanceof SwingScript) {
        SwingScript script = (SwingScript) comp;
        this.xulDomContainer.addEventHandler(script.getId(), script.getSrc());
      } 
    }
  }

  public void close() {
    frame.setVisible(false);
  }
  
  public boolean isClosed(){
    return !frame.isVisible();
  }

  public void open() {
    frame.setVisible(true);
  }


  @Override
  public void replaceChild(XulComponent oldElement, XulComponent newElement) {
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }
}
