package org.pentaho.ui.xul.swing.tags;

import javax.swing.JLabel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTreeCell extends SwingElement implements XulTreeCell {
	
	private JLabel label;
	private boolean value;
	
	public SwingTreeCell(XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("treecell");
		label = new JLabel();
		managedObject = label;
	}
	
	public SwingTreeCell(XulComponent parent) {
		super("treecell");
		label = new JLabel();
		managedObject = label;
	}
	
	public String getLabel() {
		return label.getText();
	}

	public String getSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getValue() {
		return value;
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEditable(boolean edit) {
		// TODO Auto-generated method stub
		
	}

	public void setLabel(String label) {
		this.label.setText(label);
	}

	public void setSrc(String srcUrl) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(Object value) {
		if(value instanceof String){
			this.value = Boolean.parseBoolean(((String) value));
		} else if(value instanceof Boolean){
			this.value = (Boolean) value;
			
		} 
	}

}
