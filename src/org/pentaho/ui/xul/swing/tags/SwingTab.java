package org.pentaho.ui.xul.swing.tags;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingTab extends SwingElement implements XulTab{
	
	private String label;
	private boolean disabled = false;
	private String onclick;
	private XulTabbox tabbox;
	public SwingTab(XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("tab");
		managedObject = "empty";
	}

	public boolean isDisabled() {
		return disabled;
	}

	public String getLabel() {
		return label;
	}

	public String getOnclick() {
		return onclick;
	}

	public void getTabbox(){
	  if(tabbox == null){
	    if(getParent() != null){
	      tabbox = (XulTabbox) getParent().getParent();
	    }
	  }
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		
		getTabbox();
		tabbox.setTabDisabledAt(disabled, getParent().getChildNodes().indexOf(this));
	}

	public void setLabel(String label) {
		this.label = label;
		
	}

	public void setOnclick(String onClick) {
		this.onclick = onClick;
		
	}
	@Override
	public void layout() {
	}
}
