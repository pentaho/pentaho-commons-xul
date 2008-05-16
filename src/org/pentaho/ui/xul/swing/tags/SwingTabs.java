package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.Element;

public class SwingTabs extends SwingElement implements XulTabs{
	public SwingTabs(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("tabs");
		this.managedObject = "empty";
	}
	
	public SwingTab getTabByIndex(int index){
		return (SwingTab) this.getChildNodes().get(index);
	}
	
	@Override
	public void layout() {
	}

}
