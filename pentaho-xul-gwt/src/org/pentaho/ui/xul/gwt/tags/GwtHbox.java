package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class GwtHbox extends AbstractGwtXulComponent implements XulHbox {

  static final String ELEMENT_NAME = "hbox"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtHbox();
      }
    });
  }
  
  public GwtHbox() {
    super(ELEMENT_NAME);
    this.orientation = Orient.HORIZONTAL;
    managedObject = container = new HorizontalPanel();
  }

  
  public void adoptAttributes(XulComponent component) {

  }
  
}
