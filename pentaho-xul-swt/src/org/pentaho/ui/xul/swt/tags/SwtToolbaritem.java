package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulToolbarbutton;
import org.pentaho.ui.xul.containers.XulToolbaritem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtToolbaritem extends AbstractSwtXulContainer implements XulToolbaritem{

  private XulComponent parent;
  private XulDomContainer container;
  private Composite panel;
  private ToolItem item;
  private static final Log logger = LogFactory.getLog(SwtToolbaritem.class);
  
  public SwtToolbaritem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("treeitem");
  
    this.parent = parent;
    this.container = domContainer;

    item = new ToolItem((ToolBar) parent.getManagedObject(), SWT.SEPARATOR);
    setManagedObject(parent.getManagedObject());
    
  }

  @Override
  public void layout() {
    if(getChildNodes().size() > 0){
      XulComponent c = getChildNodes().get(0);
      Control control = (Control) c.getManagedObject();
      control.pack();
      item.setControl(control);
      item.setWidth(control.getSize().x);
    }
    ((ToolBar) parent.getManagedObject()).pack();
  }
  
  
  
  
}
