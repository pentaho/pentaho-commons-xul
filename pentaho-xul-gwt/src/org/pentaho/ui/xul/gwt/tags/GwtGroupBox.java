package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GroupBoxPanel;
import org.pentaho.ui.xul.util.Orient;

public class GwtGroupBox extends AbstractGwtXulContainer implements XulGroupbox {

  static final String ELEMENT_NAME = "groupbox"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtGroupBox();
      }
    });
  }

  public GwtGroupBox() {
    super(ELEMENT_NAME);
    this.orientation = Orient.VERTICAL;
    managedObject = container = new GroupBoxPanel();
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    if (srcEle.hasAttribute("caption")) {
      setCaption(srcEle.getAttribute("caption"));  
    }
  }
  
  public void resetContainer(){
    container.clear();
  }
  
  public void setCaption(String caption){
    ((GroupBoxPanel)container).setCaption(caption);
    
  }
  
  @Override
  public void layout(){
    super.layout();
    for(XulComponent comp : this.getChildNodes()){
      if(comp instanceof GwtCaption){
        this.setCaption(((GwtCaption) comp).getLabel());
      }
    }
  }
  
  public Orient getOrientation() {
    return Orient.VERTICAL;
  }

  @Override
  public void replaceChild(Element oldElement, Element newElement) {
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }
  

  
  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("caption") != null){
      setCaption(component.getAttributeValue("caption"));
    }
  }
}
