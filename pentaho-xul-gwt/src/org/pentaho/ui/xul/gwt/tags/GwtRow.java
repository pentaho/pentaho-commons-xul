package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtRow extends AbstractGwtXulContainer implements XulRow {

  public static void register() {
    GwtXulParser.registerHandler("row", //$ NON-NLS-1$
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtRow();
      }
    });
  }
  
  public GwtRow() {
    super("row"); //$NON-NLS-1$
    this.managedObject = "empty";
  }
  

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }


  public void addComponentAt(XulComponent component, int idx) {
    // TODO Auto-generated method stub
    
  }

  public void removeComponent(XulComponent component) {
    // TODO Auto-generated method stub
    
  }
}
