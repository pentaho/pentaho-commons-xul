package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulToolbarseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtToolbarseparator extends AbstractGwtXulComponent implements XulToolbarseparator{
 
  public GwtToolbarseparator() {
    super("toolbarseparator");
  }

  public static void register() {
    GwtXulParser.registerHandler("toolbarseparator", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbarseparator();
      }
    });
  }


}

  