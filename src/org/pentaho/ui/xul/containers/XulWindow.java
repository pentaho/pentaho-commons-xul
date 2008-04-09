/**
 * 
 */
package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;

/**
 * Typically the top level container, the XulWindow
 * interface provides the necessary methods to display
 * and dispose of an independent container. Models the XUL window widget. 
 * 
 * @author nbaker
 * 
 */
public interface XulWindow extends XulContainer{
  
  /**
   * This is the event that gets fired once the 
   * XUL parser and loader have completed their work. 
   */
  public static final int EVENT_ON_LOAD = 555;

  /**
   * Sets the title of the application window.
   * @param title The application's title text. 
   */
  public void setTitle(String title);

  /**
   *  
   * @return The width of this window. 
   */
  public int getWidth();
  
  /**
   * 
   * @return The height of this window. 
   */
  public int getHeight();
  
  /**
   * Creates a reference to the DOM container that will be managing this window and its events.
   * @param xulDomContainer the container holding this document. 
   */
  public void setXulDomContainer(XulDomContainer xulDomContainer);
  
  /**
   * 
   * @return the DOM container managing this document. 
   */
  public XulDomContainer getXulDomContainer();
  
  /**
   * Execute the method passed, with any args
   * as parameters. This invokation is used for 
   * plumbing event handlers to the event methods. 
   * 
   * @param method The method to execute
   * @param args Any parameters needed for the method. 
   */
  public void invoke(String method, Object[] args);
  
  /**
   * Sets the method name to invoke during the onload event 
   * for this window.
   *  
   * @param onload The method name, in the form of [handlerId.medthodName()].
   */
  public void setOnload(String onload);
  
  /**
   * 
   * @return The method string used for the onload event. 
   */
  public String getOnload();
  
  /**
   *  Open the window for display. 
   */
  public void open();
  
  /**
   *  Close the window, and return control to the executing 
   *  program.
   */
  public void close();
  
  public boolean isClosed();
  
  
}
