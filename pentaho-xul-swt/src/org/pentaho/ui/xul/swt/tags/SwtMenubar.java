package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtMenubar extends AbstractSwtXulContainer implements XulMenubar {

  private Menu menuBar;

  private XulComponent parent;
  
  public SwtMenubar(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menubar");

    menuBar = new Menu ((Shell) parent.getManagedObject(), SWT.BAR);
    ((Shell) parent.getManagedObject()).setMenuBar(menuBar);
    setManagedObject(menuBar);
    this.parent = parent;
  }
  

  @Override
  public XulComponent getParent() {
    return parent;
  }

  @Override
  public void layout() {
//    for (XulComponent comp : children) {
//      if (comp instanceof SwtMenu) {
//        this.menuBar.add((JMenu) ((SwingMenu) comp).getManagedObject());
//        
//      }
//    }
    initialized = true;
  }


}
