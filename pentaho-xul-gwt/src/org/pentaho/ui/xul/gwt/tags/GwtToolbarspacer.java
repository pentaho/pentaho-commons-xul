package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulToolbarspacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtToolbarspacer extends AbstractGwtXulComponent implements XulToolbarspacer{

  public GwtToolbarspacer(){
    super("toolbarspacer");
  }
  
  public static void register() {
    GwtXulParser.registerHandler("toolbarspacer", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbarspacer();
      }
    });
  }

  public void adoptAttributes(XulComponent component) {
  }
}

  