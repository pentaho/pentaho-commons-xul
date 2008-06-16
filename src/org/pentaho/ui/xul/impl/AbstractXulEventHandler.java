package org.pentaho.ui.xul.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 *
 */
public abstract class AbstractXulEventHandler implements XulEventHandler, XulEventSource {
  protected XulDomContainer xulDomContainer;
  protected Document document;
  protected String name;
  protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
  
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

  public Object getData() {
    return null;
  }

  public void setData(Object data) {}
  
  /*
   * Convenience methods for data binding
   */
  public void bind(XulEventSource model, String modelPropertyName, String xulComponentElementId, String xulComponentPropertyName){
    getXulDomContainer().createBinding(model, modelPropertyName, xulComponentElementId, xulComponentPropertyName);
  }
  public void bind(String srcXulComponentElementId, String srcXulComponentPropertyName, String destXulComponentElementId, String destXulComponentPropertyName){
    getXulDomContainer().createBinding(srcXulComponentElementId, srcXulComponentPropertyName, destXulComponentElementId, destXulComponentPropertyName);
  }
  
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.removePropertyChangeListener(listener);
  }
  
  protected void firePropertyChange(String attr, Object previousVal, Object newVal){
    changeSupport.firePropertyChange(attr, previousVal, newVal);
  }
}
