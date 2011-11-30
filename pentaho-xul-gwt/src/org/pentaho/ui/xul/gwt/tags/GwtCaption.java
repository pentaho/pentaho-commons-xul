package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtCaption extends AbstractGwtXulComponent implements XulCaption {
  
  static final String ELEMENT_NAME = "caption"; //$NON-NLS-1$

  private enum Property{
    LABEL
  }
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
    setManagedObject(caption);
  }


  @Override
  public void setAttribute(String name, String value) {
    super.setAttribute(name, value);
    try{
      Property prop = Property.valueOf(name.replace("pen:", "").toUpperCase());
      switch (prop) {
        case LABEL: setLabel(value);
          break;
      }
    } catch(IllegalArgumentException e){
      System.out.println("Could not find Property in Enum for: "+name+" in class"+getClass().getName());
    }
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    if (srcEle.hasAttribute("label")) {
      setLabel(srcEle.getAttribute("label"));  
    }
  }
  
  public String getLabel() {
    return caption;
  }
  
  public void setLabel(String caption) {
    this.caption = caption;
    setManagedObject(this.caption);
  }

  public void layout() {
  }
  

}
