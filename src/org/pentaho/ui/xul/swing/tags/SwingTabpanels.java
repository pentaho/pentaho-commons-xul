package org.pentaho.ui.xul.swing.tags;

import javax.swing.JTabbedPane;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.Element;

public class SwingTabpanels extends SwingElement implements XulTabpanels{
	public SwingTabpanels(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("tabpanels");
		this.managedObject = "empty";
	}
	
	public XulTabpanel getTabpanelByIndex(int index) {
		return (SwingTabpanel) this.children.get(index);
	}
	
	@Override
	public void layout() {
	}
	
}
