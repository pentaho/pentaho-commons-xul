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
   * @return any data associated with events that have been executed.
   */
  public abstract Object getData();
}
