package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtTabpanels extends SwtElement implements XulTabpanels{
  
  private XulTabbox tabbox;
  public SwtTabpanels(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tabpanels");
    tabbox = (XulTabbox)parent;
    this.managedObject = "empty";
  }
  
  protected XulTabbox getTabbox(){
    return tabbox;
  }
  
  public XulTabpanel getTabpanelByIndex(int index) {
    return (XulTabpanel) this.children.get(index);
  }
  

  @Override
  public void addComponent(XulComponent c) {
    super.addComponent(c);
    if(getParent() != null){
      ((XulTabbox) getParent()).addTabpanel(this.children.indexOf(c));
    }
  }

  
  @Override
  public void layout() {
  }
}

  