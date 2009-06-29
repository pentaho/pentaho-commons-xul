package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.containers.XulColumn;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtColumn extends AbstractGwtXulContainer implements XulColumn {

  public static void register() {
    GwtXulParser.registerHandler("column", //$ NON-NLS-1$
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtColumn();
      }
    });
  }
  
  public GwtColumn() {
    super("column"); //$NON-NLS-1$
    this.managedObject = "empty";
  }

  public void layout(){

  }
}
