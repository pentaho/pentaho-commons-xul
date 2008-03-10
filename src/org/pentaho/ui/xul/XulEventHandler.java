/**
 * 
 */
package org.pentaho.ui.xul;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulWindowContainer;

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
