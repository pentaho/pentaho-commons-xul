/**
 * 
 */
package org.pentaho.ui.xul;

import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 *
 */
public abstract class XulEventHandler {
  protected XulDomContainer xulDomContainer;
  protected Document document;
  
  public void setXulDomContainer(XulDomContainer xulDomContainer) {
    this.xulDomContainer = xulDomContainer;
    this.document = xulDomContainer.getDocumentRoot();
  }
  
  /**
   * A generic way of returning data from event handlers... can we do better than this?
   * Handle return values from invoked methods? possibly?  
   * @return any data associated with events that have been executed.
   */
  public abstract Object getData();

  /**
   * A generic way of passing data to the event handler. It seems we should maybe accept 
   * parameters instead of doing this. 
   * @param any data events may want to operate on.
   */
  public abstract void  setData(Object data);

}
