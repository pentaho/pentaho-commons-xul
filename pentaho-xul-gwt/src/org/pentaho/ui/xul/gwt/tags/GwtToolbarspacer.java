package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulToolbarspacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.SimplePanel;

public class GwtToolbarspacer extends AbstractGwtXulContainer implements XulToolbarspacer{

  private SimplePanel panel = new SimplePanel();
  
  public GwtToolbarspacer(){
    super("toolbarspacer");
    this.managedObject = panel;
    //Do not change. GWT widgets Toolbar keys off this to know that the panel is suposed to act as a spacer
    panel.setStyleName("spacer");
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container){
    super.init(srcEle, container);
    String vis = srcEle.getAttribute("pen:visible");
    if(vis != null && !vis.equals("")){
      setVisible("true".equals(vis));
    }
  }
  
  public static void register() {
    GwtXulParser.registerHandler("toolbarspacer", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbarspacer();
      }
    });
  }

  public void adoptAttributes(XulComponent component) {
    if(component.getAttributeValue("pen:visible") != null){
      setVisible("true".equals(component.getAttributeValue("pen:visible")));
    }
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    panel.setVisible(visible);
    //need to collapse space if not visible (style="visibility:hidden" preserves space)
    if(visible){
      panel.setWidth(this.getWidth()+"px");
    } else {
      panel.setWidth("0px");
    }
  }

  @Override
  public void setWidth(int width) {
    super.setWidth(width);
    if(this.isVisible()){
      panel.setWidth(width+"px");
    }
  }
  
  
}

  