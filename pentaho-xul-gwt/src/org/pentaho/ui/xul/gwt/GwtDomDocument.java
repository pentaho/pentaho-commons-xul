package org.pentaho.ui.xul.gwt;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;

public class GwtDomDocument extends GwtDomElement implements Document {
  
  Element rootElement;
  XulDomContainer container;
  
  public GwtDomDocument() {
    super("DOCUMENT");
  }
  
  public XulComponent getRootElement() {
    // TODO Auto-generated method stub
    return (XulComponent) getFirstChild();
  }



  public void addOverlay(String src) throws XulException {
    
        // TODO Auto-generated method stub 
      
  }

  public XulComponent createElement(String elementName) throws XulException {
    return this.container.getXulLoader().createElement(elementName);
  }

  public void invokeLater(Runnable runnable) {
    
        // TODO Auto-generated method stub 
      
  }

  public boolean isRegistered(String elementName) {
    return false;
  }

  public void loadFragment(String id, String src) throws XulException {
    
        // TODO Auto-generated method stub 
      
  }

  public void removeOverlay(String src) throws XulException {
    
        // TODO Auto-generated method stub 
      
  }

  public void setXulDomContainer(XulDomContainer container) {
    this.container = container;
  }

  public void addBinding(Binding bind) {
    ((GwtXulDomContainer) this.container).addBinding(bind);
    
  }

  public void addInitializedBinding(Binding b) {
    ((GwtXulDomContainer) this.container).addBinding(b);
    
  }
}
