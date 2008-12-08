package org.pentaho.ui.xul.swt.tags;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swing.tags.SwingMenu;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtMenubar extends SwtElement implements XulMenubar {

  private Menu menuBar;

  private XulComponent parent;
  
  public SwtMenubar(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menubar");

    children = new ArrayList<XulComponent>();

    menuBar = new Menu ((Shell) parent.getManagedObject(), SWT.BAR);
    ((Shell) parent.getManagedObject()).setMenuBar(menuBar);
    managedObject = menuBar;
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

  @Override
  public void addComponent(XulComponent c) {
    super.addComponent(c);
//    if (initialized) {
//      resetContainer();
//      layout();
//    }
  }

}
