package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.toolbar.Toolbar;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarButton;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarGroup;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulToolbarbutton;
import org.pentaho.ui.xul.components.XulToolbarseparator;
import org.pentaho.ui.xul.components.XulToolbarspacer;
import org.pentaho.ui.xul.components.XulToolbarspring;
import org.pentaho.ui.xul.containers.XulToolbar;
import org.pentaho.ui.xul.containers.XulToolbarset;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtToolbar extends AbstractGwtXulContainer implements XulToolbar{
  private String toolbarName;
  private ToolbarMode mode = ToolbarMode.ICONS;
  private Toolbar toolbar = new Toolbar();
  private boolean loaded;
  
  public static void register() {
    GwtXulParser.registerHandler("toolbar", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbar();
      }
    });
  }
  
  public GwtToolbar(){
    super("toolbar");
    managedObject = toolbar;
  }
  
  public String getToolbarName() {
    return toolbarName;
  }

  public void setToolbarName(String name) {
    this.toolbarName = name;
  }

  public void setMode(ToolbarMode mode) {
    this.mode = mode;
  }
  
  public ToolbarMode getMode(){
    return mode;
  }

  @Override
  public void layout() {
    for(XulComponent c : this.getChildNodes()){
      add(c);
    }
    if(!loaded){
      loaded = true;
    }
  }
  
  private void add(XulComponent c){
    if(c instanceof XulToolbarset){
      ToolbarGroup group = (ToolbarGroup) c.getManagedObject();
      toolbar.add(group);
    } else if(c instanceof XulToolbarbutton){
      toolbar.add((ToolbarButton) c.getManagedObject());
    } else if(c instanceof XulToolbarseparator){
      toolbar.add(Toolbar.SEPARATOR);
    } else if(c instanceof XulToolbarspring){
      toolbar.add(Toolbar.GLUE);
    } else if(c instanceof XulToolbarspacer){
      toolbar.addSpacer(((XulToolbarspacer) c).getWidth());
    }
  }


  @Override
  public void addChild(Element element) {
    super.addChild(element);
    if(loaded == true){
      toolbar.removeAll();
      this.layout();
    }
  }

  @Override
  public void addChildAt(Element element, int idx) {
    super.addChildAt(element, idx);
    if(loaded == true){
      toolbar.removeAll();
      this.layout();
    }
  }

  @Override
  public void removeChild(Element element) {
    super.removeChild(element);
    XulComponent child = (XulComponent) element;
    if(child instanceof XulToolbarbutton){
      toolbar.remove(((ToolbarButton) child.getManagedObject()).getPushButton());
    }
    
  }

  public void adoptAttributes(XulComponent component) {

  }
}
