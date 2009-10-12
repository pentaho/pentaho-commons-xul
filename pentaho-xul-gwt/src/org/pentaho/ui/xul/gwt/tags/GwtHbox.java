package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GwtUIConst;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Align;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class GwtHbox extends AbstractGwtXulContainer implements XulHbox {

  static final String ELEMENT_NAME = "hbox"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtHbox();
      }
    });
  }
  
  Align alignment;
  
  public GwtHbox() {
    super(ELEMENT_NAME);
    this.orientation = Orient.HORIZONTAL;
    HorizontalPanel hp;
    container = hp = new HorizontalPanel();
    setManagedObject(container);
    hp.setSpacing(GwtUIConst.PANEL_SPACING);    // IE_6_FIX, move to CSS
    hp.setStyleName("hbox");
    
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setAlign(srcEle.getAttribute("align"));
  }
  
  public void adoptAttributes(XulComponent component) {
    super.adoptAttributes(component);
    setPadding(component.getPadding());
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
  
  public void layout() {
    super.layout();
  }
  
  @Override
  public void setBgcolor(String bgcolor) {
    if(container != null) {
      container.getElement().getStyle().setProperty("backgroundColor", bgcolor);
    }
  }
}

