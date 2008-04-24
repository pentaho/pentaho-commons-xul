package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.swing.tags.SwingTab;

public interface XulTabs extends XulContainer {
	public SwingTab getTabByIndex(int index);
}
