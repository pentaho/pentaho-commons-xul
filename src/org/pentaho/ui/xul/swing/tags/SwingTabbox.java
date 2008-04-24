package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ToolTipManager;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTabbox extends SwingElement implements XulTabbox{

	private JTabbedPane tabpane;
	private SwingTabpanels panels;
	private SwingTabs tabs;
	private int selectedIndex;
	
	
	public SwingTabbox(XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("tabbox");
		tabpane = new JTabbedPane();
		this.managedObject = tabpane;
	}
	
  @Override
  public void addChild(Element ele) {
     super.addChild(ele);
     
     if(ele instanceof SwingTabs){
    	 this.tabs = (SwingTabs) ele;
     } else if(ele instanceof SwingTabpanels){
    	 this.panels = (SwingTabpanels) ele;
     }
     
  }
	
	public int getSelectedIndex() {
		return tabpane.getSelectedIndex();
	}

	public XulTabpanel getSelectedPanel() {
		return panels.getTabpanelByIndex(getSelectedIndex());
	}

	public XulTabpanels getTabpanels() {
		return panels;
	}

	public XulTabs getTabs() {
		return tabs;
	}

	public void setSelectedIndex(int index) {
		selectedIndex = index;
		if(tabpane.getTabCount() > 0){ // component instantiated
			tabpane.setSelectedIndex(selectedIndex);
		}
	}

	@Override
	public void layout() {
		for(int i=0; i<tabs.getChildNodes().size(); i++){
			tabpane.add((Component) this.panels.getTabpanelByIndex(i).getManagedObject(), tabs.getTabByIndex(i).getLabel());
			tabpane.setEnabledAt(i, ! tabs.getTabByIndex(i).isDisabled());
		}
		tabpane.setSelectedIndex(selectedIndex);
	}

}
