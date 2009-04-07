package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTabpanels extends AbstractSwingContainer implements XulTabpanels {
  public SwingTabpanels(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tabpanels");
    this.managedObject = "empty";
  }

  public XulTabpanel getTabpanelByIndex(int index) {
    return (index < getChildNodes().size()) ? (SwingTabpanel) this.getChildNodes().get(index) : null;
  }

  @Override
  public void removeChild(Element ele) {
    int idx = getChildNodes().indexOf(ele);
    super.removeChild(ele);
    ((XulTabbox) getParent()).removeTabpanel(idx);
  }

  @Override
  public void layout() {
    initialized = true;
    if (getParent() != null) {
    	((SwingElement)getParent()).layout();
    }
  }

}
