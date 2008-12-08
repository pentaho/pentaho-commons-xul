package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulSpacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtSpacer extends AbstractGwtXulComponent implements XulSpacer {
  
  static final String ELEMENT_NAME = "spacer"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtSpacer();
      }
    });
  }
  
  public GwtSpacer() {
    super(ELEMENT_NAME);
  }
  
  public void setWidth(int size){
//    strut = Box.createHorizontalStrut(size);
//    managedObject = strut;
  }
  public void setHeight(int size){
//    strut = Box.createVerticalStrut(size);
//    managedObject = strut;
  }

  public void layout() {
  }
  

  public void adoptAttributes(XulComponent component) {
    
  }
}
