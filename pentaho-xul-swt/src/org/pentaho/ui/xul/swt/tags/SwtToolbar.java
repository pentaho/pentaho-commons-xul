package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulToolbar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtToolbar extends AbstractSwtXulContainer implements XulToolbar{

  ToolBar toolbar;
  public SwtToolbar(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("toolbar");
    
    String attr = self.getAttributeValue("parenttoouter");
    Object shell = (attr != null && attr.equals("true") && domContainer.getOuterContext() != null) 
      ? domContainer.getOuterContext() 
      : null; 
    if(shell != null && shell instanceof Shell){
      toolbar = new ToolBar((Shell) shell, SWT.HORIZONTAL);
    } else if(shell != null && shell instanceof Composite){
      toolbar = new ToolBar((Composite) shell, SWT.HORIZONTAL);
    } else {
      toolbar = new ToolBar((Composite) parent.getManagedObject(), SWT.HORIZONTAL);
    }
    setManagedObject(toolbar);
  }

  public ToolbarMode getMode() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getToolbarName() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setMode(ToolbarMode mode) {
    // TODO Auto-generated method stub
    
  }

  public void setToolbarName(String name) {
    // TODO Auto-generated method stub
    
  }
  
  
}
