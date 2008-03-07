/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.lang.reflect.Method;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulTagHandler;
import org.pentaho.ui.xul.containers.XulWindow;


/**
 * @author OEM
 *
 */
public class SwingWindow extends XulElement implements XulWindow  {

  JFrame frame;
  private XulEventHandler eventHandler;
  private int width;
  private int height;
  private Document rootDocument;
  private Box contentPane;
  private XulRunner xulRunner;
  
  public SwingWindow(String title){
    super("window");
    frame = new JFrame(title);
    contentPane =  Box.createVerticalBox();
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(contentPane, BorderLayout.NORTH);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    managedObject = frame;
  }
  
  private JFrame getFrame(){
    return (JFrame) managedObject;
  }

  public void add(XulComponent c){
    Component component = (Component) c.getManagedObject();
    //force components to fill horizontal space
    component.setMaximumSize(null);
    component.setPreferredSize(null);
    
    contentPane.add(component);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulPage#setTitle(java.lang.String)
   */
  public void setTitle(String title) {
    frame.setTitle(title);
  }

  
  public void setEventHandlerClass(String str){
    try{
      Class cls = Class.forName(str);
      eventHandler = (XulEventHandler) cls.newInstance();
      eventHandler.setXulRunner(this.xulRunner);
      
    } catch(ClassNotFoundException e){
      System.out.println("backing class not found");
      e.printStackTrace(System.out);
    } catch(Exception e){
      System.out.println("Error with Backing class creation");
      e.printStackTrace(System.out);
    }
  }
  
  public XulEventHandler getEventHandler(){
    return eventHandler;
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
      method = method.replace("()", "");
      Method m = eventHandler.getClass().getMethod(method, new Class[0]);
      m.invoke(eventHandler, args);
      
    } catch(Exception e){
      System.out.println("Error invoking method: "+method);
      e.printStackTrace(System.out);
    }
  }
  
  public void setRootDocument(Document document){
    this.rootDocument = document;
  }
  
  public void setXulRunner(XulRunner xulRunner){
    this.xulRunner = xulRunner;
  }
}
