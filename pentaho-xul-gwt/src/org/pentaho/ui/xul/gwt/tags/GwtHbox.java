package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

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
  
  String align;
  
  public GwtHbox() {
    super(ELEMENT_NAME);
    this.orientation = Orient.HORIZONTAL;
    managedObject = container = new HorizontalPanel();
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle) {
    super.init(srcEle);
    setAlign(srcEle.getAttribute("align"));
  }
  
  public void adoptAttributes(XulComponent component) {

  }
  
  public void layout() {
    HorizontalPanel hpanel = new HorizontalPanel();
    managedObject = container = hpanel;
    if ("left".equals(align) || "start".equals(align)) {
      hpanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
    } else if ("right".equals(align) || "end".equals(align)) {
      hpanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
    } else if ("center".equals(align) || "center".equals(align)) {
      hpanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
    }
    super.layout();
  }
  
  public void setAlign(String align) {
    this.align = align;
  }
  
}

