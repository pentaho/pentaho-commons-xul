package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulOverlay;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtOverlay extends AbstractGwtXulComponent implements XulOverlay{

  
  public static void register() {
    GwtXulParser.registerHandler("overlay", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtOverlay();
      }
    });
  }
  
  public GwtOverlay(){
    super("overlay");
  }
  public void init(com.google.gwt.xml.client.Element srcEle) {
   super.init(srcEle);
  }
  
  public String getSrc() {
    return "";  
  }

  public void setSrc(String src) {
      
  }

  public void adoptAttributes(XulComponent component) {
  }

  public boolean isDisabled() {
    return false;
  }

  public void setDisabled(boolean disabled) {
  }

  @Override
  public void layout() {
    //No layout required
  }

  
  
}

  