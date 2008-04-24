package org.pentaho.ui.xul.swing.tags;

import javax.swing.JTabbedPane;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTabpanels extends SwingElement implements XulTabpanels{
	public SwingTabpanels(XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("tabpanels");
		this.managedObject = "empty";
	}
	
	public XulTabpanel getTabpanelByIndex(int index) {
		return (XulTabpanel) this.children.get(index);
	}
	
	@Override
	public void layout() {
	}
	
}
