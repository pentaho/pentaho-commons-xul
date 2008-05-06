/**
 * 
 */
package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.dom.Document;

/**
 * Try to use a concrete implementation of {@link XulEventHandler} instead of this.
 * @author OEM
 *
 */
@Deprecated
public abstract class AbstractXulEventHandler implements XulEventHandler {
  protected XulDomContainer xulDomContainer;
  protected Document document;
  protected String name;
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.impl.XulEventHandler#getName()
   */
  public String getName() {
    return name;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.impl.XulEventHandler#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.impl.XulEventHandler#setXulDomContainer(org.pentaho.ui.xul.XulDomContainer)
   */
  public void setXulDomContainer(XulDomContainer xulDomContainer) {
    this.xulDomContainer = xulDomContainer;
    this.document = xulDomContainer.getDocumentRoot();
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.impl.XulEventHandler#getXulDomContainer()
   */
  public XulDomContainer getXulDomContainer(){
    return this.xulDomContainer;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.impl.XulEventHandler#getData()
   */
  public abstract Object getData();

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.impl.XulEventHandler#setData(java.lang.Object)
   */
  public abstract void  setData(Object data);

}
