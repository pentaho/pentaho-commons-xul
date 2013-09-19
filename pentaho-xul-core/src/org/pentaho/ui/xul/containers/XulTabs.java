package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.components.XulTab;

public interface XulTabs extends XulContainer {
	public XulTab getTabByIndex(int index);
	public int getTabCount();
}
