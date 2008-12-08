package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtCaption extends AbstractGwtXulComponent implements XulCaption {
  
  static final String ELEMENT_NAME = "caption"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtCaption();
      }
    });
  }
  
  private String caption;

  public GwtCaption() {
    super(ELEMENT_NAME);
    managedObject = caption;
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle) {
    super.init(srcEle);
    if (srcEle.hasAttribute("label")) {
      setLabel(srcEle.getAttribute("label"));  
    }
  }
  
  public String getLabel() {
    return caption;
  }
  
  public void setLabel(String caption) {
    managedObject = this.caption = caption;
  }

  public void layout() {
  }
  

  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("label") != null){
      setLabel(component.getAttributeValue("label"));
    }
  }
}
