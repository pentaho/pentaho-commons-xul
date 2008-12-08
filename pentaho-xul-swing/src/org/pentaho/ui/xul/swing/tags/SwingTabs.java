package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTabs extends SwingElement implements XulTabs{
	public SwingTabs(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("tabs");
		this.managedObject = "empty";
	}
	
	public SwingTab getTabByIndex(int index){
		return (SwingTab) this.getChildNodes().get(index);
	}

  @Override
  public void removeChild(Element ele) {
    super.removeChild(ele);
    int idx = children.indexOf(ele);
    children.remove(idx);
    ((XulTabbox) getParent()).removeTab(idx);
  }
  
  @Override
  public void addComponent(XulComponent comp){
    super.addComponent(comp);
    if(initialized){
      ((SwingTabbox) getParent()).layout();
    }
  }

  @Override
  public void addComponentAt(XulComponent comp, int pos){
    super.addComponentAt(comp, pos);
    if(initialized){
      ((SwingTabbox) getParent()).layout();
    }
  }
  
  public int getTabCount() {
    return this.getChildNodes().size();
  }

}
