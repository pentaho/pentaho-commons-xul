package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTabpanels extends SwingElement implements XulTabpanels {
  public SwingTabpanels(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tabpanels");
    this.managedObject = "empty";
  }

  public XulTabpanel getTabpanelByIndex(int index) {
    return (index < children.size()) ? (SwingTabpanel) this.children.get(index) : null;
  }

  @Override
  public void addComponent(XulComponent comp) {
    super.addComponent(comp);
    if (initialized) {
      ((SwingTabbox) getParent()).layout();
    }
  }

  @Override
  public void removeChild(Element ele) {
    super.removeChild(ele);
    int idx = children.indexOf(ele);
    children.remove(idx);
    System.out.println("Tabpanel index: " + idx);
    ((XulTabbox) getParent()).removeTabpanel(idx);
  }

  @Override
  public void layout() {
    initialized = true;
  }

}
