/**
 * 
 */
package org.pentaho.ui.xul;

import org.pentaho.ui.xul.XulWindowContainer;
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
}
