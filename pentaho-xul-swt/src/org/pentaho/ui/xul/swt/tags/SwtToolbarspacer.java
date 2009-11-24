package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulToolbarspacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtToolbarspacer extends AbstractSwtXulContainer implements XulToolbarspacer{

  ToolItem spacer;
  public SwtToolbarspacer(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("toolbarspacer");

    
    spacer = new ToolItem((ToolBar) parent.getManagedObject(), SWT.SEPARATOR);
    setManagedObject(spacer);
  }

}
