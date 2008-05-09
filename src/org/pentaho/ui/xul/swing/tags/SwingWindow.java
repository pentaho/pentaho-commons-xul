/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.TextAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
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
  private static final Log logger = LogFactory.getLog(SwingElement.class);
  
  JFrame frame;

  private int width;

  private int height;

  private Document rootDocument;

  private XulDomContainer xulDomContainer;

  private String onload;
  
  private Clipboard clipboard;
  
  /**
   * Xul Buttons can belong to radio button groups without a "radiogroup" element in the DOM
   * This collection facilitates this behavior.
   */
  private Map<String, ButtonGroup> anonymousButtonGroups = new HashMap<String, ButtonGroup>();

  public SwingWindow(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("window");
    frame = new JFrame();
    this.xulDomContainer = domContainer;

    this.orientation = Orient.VERTICAL;

    children = new ArrayList<XulComponent>();

    container = new JPanel(new GridBagLayout());
    //container.setBorder(BorderFactory.createLineBorder(Color.green));
    managedObject = container;

    clipboard = Toolkit.getDefaultToolkit( ).getSystemClipboard( );
    
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
        try{
        	this.xulDomContainer.addEventHandler(script.getId(), script.getSrc());
        } catch(XulException e){
        	logger.error("Error adding Event Handler to Window: "+script.getSrc(), e);
        }
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
  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException{
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }

  
	public void copy() throws XulException{
		TextAction act = new DefaultEditorKit.CopyAction();
		act.actionPerformed(new ActionEvent(this.getManagedObject(), 999, "copy"));
		
	}


	public void cut() {
		TextAction act = new DefaultEditorKit.CutAction();
		act.actionPerformed(new ActionEvent(this.getManagedObject(), 999, "cut"));
	}


	public void paste() {
		TextAction act = new DefaultEditorKit.PasteAction();
		act.actionPerformed(new ActionEvent(this.getManagedObject(), 999, "paste"));
	}
	
	public ButtonGroup getButtonGroup(String group){
	  if(anonymousButtonGroups.containsKey(group)){
	    return anonymousButtonGroups.get(group);
	  } else {
	    ButtonGroup grp = new ButtonGroup();
	    anonymousButtonGroups.put(group, grp);
	    return grp;
	  }
	}
	
}
