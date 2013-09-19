package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.components.XulTabpanel;

public interface XulTabpanels extends XulContainer{
	public XulTabpanel getTabpanelByIndex(int index);
}
