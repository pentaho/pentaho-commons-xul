package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtTabPanel  extends AbstractGwtXulContainer implements XulTabpanel {

  static final String ELEMENT_NAME = "tabpanel"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTabPanel();
      }
    });
  }
  
  protected VerticalPanel verticalPanel;

  public GwtTabPanel() {
    this(Orient.VERTICAL);
  }

  public GwtTabPanel(Orient orient) {
    super(ELEMENT_NAME);
    this.orientation = orient;
    verticalPanel = new VerticalPanel();
    managedObject =  container = verticalPanel; 
  }
  
  public void resetContainer(){
    for(int i=0; i< verticalPanel.getWidgetCount();i++) {
      verticalPanel.remove(i);
    }
    verticalPanel = new VerticalPanel();
  }

  @Override
  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException{
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }

}
