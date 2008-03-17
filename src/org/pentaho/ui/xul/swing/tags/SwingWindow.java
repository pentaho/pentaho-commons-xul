/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulTagHandler;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swt.Orient;


/**
 * @author OEM
 *
 */
public class SwingWindow extends SwingElement implements XulWindow  {

  JFrame frame;
  private int width;
  private int height;
  private Document rootDocument;
  private XulDomContainer xulDomContainer;

  
  public SwingWindow(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("window");
    frame = new JFrame();

    
    this.orientation = Orient.VERTICAL;
    
    children = new ArrayList<XulComponent>();
    
    container = new JPanel(new GridBagLayout());
    container.setBorder(BorderFactory.createLineBorder(Color.green));
    managedObject = container;
    
    
    gc = new GridBagConstraints();
    gc.gridy = gc.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = gc.REMAINDER;
    gc.insets = new Insets(2,2,2,2);
    gc.fill = gc.HORIZONTAL;
    gc.anchor = gc.NORTHWEST;
    gc.weightx = 1;
    
    
    
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(container, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    managedObject = frame;
  }
  
  private JFrame getFrame(){
    return (JFrame) managedObject;
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
  
  public void setHeight(int height){
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
  
  public void setWidth(int width){
    this.width = width;
    frame.setSize(new Dimension(this.width, this.height));
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulWindow#invoke(java.lang.String, java.lang.Object[])
   */
  public void invoke(String method, Object[] args) {
    
    try{
      if(method.indexOf('.') == -1){
        throw new IllegalArgumentException("method call does not follow the pattern [EventHandlerID].methodName()");
      }
      
      method = method.replace("()", "");
      String[] pair = method.split("\\.");
      String eventID = pair[0];
      String methodName = pair[1];
      
      XulEventHandler eventHandler = this.xulDomContainer.getEventHandler(eventID);
      Method m = eventHandler.getClass().getMethod(methodName, new Class[0]);
      m.invoke(eventHandler, args);
      
    } catch(Exception e){
      System.out.println("Error invoking method: "+method);
      e.printStackTrace(System.out);
    }
  }
  
  public void setRootDocument(Document document){
    this.rootDocument = document;
  }
  
  public void setXulDomContainer(XulDomContainer xulDomContainer){
    this.xulDomContainer = xulDomContainer;
  }

  public XulDomContainer getXulDomContainer() {
    return xulDomContainer;
  }

  
}
