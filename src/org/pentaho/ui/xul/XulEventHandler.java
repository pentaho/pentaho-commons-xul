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
  protected XulWindowContainer xulWindowContainer;
  protected Document document;
  
  public void setXulWindowContainer(XulWindowContainer xulWindowContainer) {
    this.xulWindowContainer = xulWindowContainer;
    this.document = xulWindowContainer.getDocumentRoot();
  }
}
