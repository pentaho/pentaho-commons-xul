package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulToolbarset;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtToolbarset extends AbstractGwtXulContainer implements XulToolbarset{

  
  public GwtToolbarset(){
    super("toolbarset");
  }
  
  public static void register() {
    GwtXulParser.registerHandler("toolbarset", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbar();
      }
    });
  }


}

  