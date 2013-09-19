package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulToolbarseparator;
import org.pentaho.ui.xul.components.XulToolbarspacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtToolbarseparator extends AbstractSwtXulContainer implements XulToolbarseparator{

  ToolItem spacer;
  public SwtToolbarseparator(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("toolbarseparator");

    
    spacer = new ToolItem((ToolBar) parent.getManagedObject(), SWT.SEPARATOR);
    setManagedObject(spacer);
  }

}
