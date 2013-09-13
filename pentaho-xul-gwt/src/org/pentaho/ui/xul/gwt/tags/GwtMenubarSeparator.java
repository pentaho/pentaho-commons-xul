package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtMenubarSeparator extends AbstractGwtXulComponent implements XulMenuseparator {

  public GwtMenubarSeparator() {
    super("menubarseparator");
  }

  public static void register() {
    GwtXulParser.registerHandler("menubarseparator", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenubarSeparator();
      }
    });
  }

}
