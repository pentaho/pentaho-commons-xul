package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.components.XulTabpanel;

public interface XulTabbox extends XulContainer{
	public int getSelectedIndex();
	public void setSelectedIndex(int index);
	public XulTabpanel getSelectedPanel();
	public XulTabs getTabs();
	public XulTabpanels getTabpanels();
}
