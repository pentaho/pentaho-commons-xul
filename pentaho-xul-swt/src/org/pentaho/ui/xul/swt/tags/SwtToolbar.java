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
  private ToolbarMode mode = ToolbarMode.FULL;
  
  public SwtToolbar(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("toolbar");
    
    String attr = self.getAttributeValue("parenttoouter");
    Object shell = (attr != null && attr.equals("true") && domContainer.getOuterContext() != null) 
      ? domContainer.getOuterContext() 
      : null; 
    if(shell != null && shell instanceof Shell){
      toolbar = new ToolBar((Shell) shell, SWT.HORIZONTAL | SWT.RIGHT);
    } else if(shell != null && shell instanceof Composite){
      toolbar = new ToolBar((Composite) shell, SWT.HORIZONTAL | SWT.RIGHT);
    } else {
      toolbar = new ToolBar((Composite) parent.getManagedObject(), SWT.HORIZONTAL | SWT.RIGHT);
    }

    setManagedObject(toolbar);
  }

  public String getMode() {
    return mode.toString();
  }

  public String getToolbarName() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public void setMode(String mode) {
    this.mode = ToolbarMode.valueOf(mode.toUpperCase());
  }

  public void setToolbarName(String name) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void layout() {
  }
  
  
  
  
}
