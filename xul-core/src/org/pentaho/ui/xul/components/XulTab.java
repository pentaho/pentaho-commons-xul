package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulTab extends XulComponent{
	
	public void setLabel(String label);
	public String getLabel();
	
	public void setOnclick(String onClick);
	public String getOnclick();
	
	public void setDisabled(boolean disabled);
	public boolean isDisabled();
	
}
