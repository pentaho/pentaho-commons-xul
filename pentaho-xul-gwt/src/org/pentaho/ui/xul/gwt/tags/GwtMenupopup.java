package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtDomElement;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtMenupopup extends AbstractGwtXulContainer implements XulMenupopup {

static final String ELEMENT_NAME = "menupopup"; //$NON-NLS-1$

  public GwtMenupopup() {
    super(ELEMENT_NAME);
    managedObject = "empty";
  }

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenupopup();
      }
    });
  }
  
  public void resetContainer() {
    super.resetContainer();
    final XulComponent component = getParent();
    if (component instanceof GwtDomElement) {
      final AbstractGwtXulContainer parent = (AbstractGwtXulContainer) component;
      parent.resetContainer();
    }
  }
}
