package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtMenu extends AbstractSwtXulContainer implements XulMenu {

  private Menu menu;
  
  private String accel = null;
  private MenuItem header;
  private Menu dropdown;
  private Shell shell;
  
  private XulComponent parent;
  
  public SwtMenu(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menu");
    if (domContainer.getOuterContext() != null){
      shell = (Shell) domContainer.getOuterContext();
    }
    
    orient = Orient.VERTICAL;
    
    // only build if a child of a top level menu, otherwise it will be build recursively by a parent
    if (shell == null){
      XulComponent p = parent;
      while(p != null && p instanceof XulRoot == false){
        p = p.getParent();
      }
      if(p != null && p instanceof XulRoot){
        shell = (Shell) p.getManagedObject();
      }
    }
    //if(parent.getManagedObject() != null){
      header = new MenuItem((Menu) parent.getManagedObject(), SWT.CASCADE);
      setManagedObject(header);
    //}
    this.parent = parent;
  }
 

  @Override
  public XulComponent getParent() {
    return parent;
  }
  
  private void addMenuChildren(Menu menuParent, List<XulComponent> children){
    for (Element comp : children) {

      for (XulComponent compInner : ((SwtMenupopup) comp).getChildNodes()) {
         if(compInner instanceof XulMenu){
          MenuItem item = new MenuItem(menuParent, SWT.CASCADE);
          Menu flyout = new Menu(shell, SWT.DROP_DOWN);
          item.setMenu(flyout);
          addMenuChildren(flyout, compInner.getChildNodes());
        } else {

          
          
        }
      }
      
    }
  }
  
  public void layout() {
    initialized = true;
  }
//
//  @Override
//  public void addComponent(XulComponent c) {
//    super.addComponent(c);
//    if (initialized) {
//      resetContainer();
//      layout();
//    }
//  }

  public String getAcceltext() {
    return accel;
  }

  public String getAccesskey() {
    return String.valueOf(header.getAccelerator());
  }
  
  public boolean isDisabled() {
    return !header.isEnabled();
  }

  public String getLabel() {
    return header.getText();
  }

  public void setAcceltext(String accel) {
    this.accel = accel;
    //menu.setAccelerator(KeyStroke.getKeyStroke(accel));
  }

  public void setAccesskey(String accessKey) {
    if(header != null){
      header.setAccelerator(accessKey.charAt(0));
    }
  }

  public void setDisabled(boolean disabled) {
    if(header != null){
      header.setEnabled(!disabled);
    }
  }

  public void setLabel(String label) {
    if(header != null){
      header.setText(label);
    }
  }

}

  