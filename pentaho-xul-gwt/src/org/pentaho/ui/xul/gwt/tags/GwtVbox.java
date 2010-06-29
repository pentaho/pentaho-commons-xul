package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GwtUIConst;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtVbox extends AbstractGwtXulContainer implements XulVbox {

  static final String ELEMENT_NAME = "vbox"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtVbox();
      }
    });
  }
  
  public GwtVbox() {
    this(ELEMENT_NAME);
  }
  
  public GwtVbox(String elementName) {
    super(elementName);
    this.orientation = Orient.VERTICAL;
    VerticalPanel vp;
    container = vp = new VerticalPanel();
    setManagedObject(container);
    vp.setSpacing(GwtUIConst.PANEL_SPACING);    // IE_6_FIX, move to CSS
    vp.setStyleName("vbox");  //$NON-NLS-1$
  }


  public void adoptAttributes(XulComponent component) {
    super.adoptAttributes(component);
    setPadding(component.getPadding());
    this.resetContainer();
    this.layout();
  }

  @Override
  @Bindable
  public void setHeight(int height) {
    super.setHeight(height);
    container.setHeight(height+"px");
  }

  @Override
  @Bindable
  public void setWidth(int width) {
    super.setWidth(width);
    container.setWidth(width+"px");
  }
  @Override  
  public void setBgcolor(String bgcolor) {
    if(container != null) {
      container.getElement().getStyle().setProperty("backgroundColor", bgcolor);
    }
  }

}
