package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtMenu extends AbstractSwtXulContainer implements XulMenu {

  private Menu menu;
  
  private String accel = null;
  private MenuItem header;
  private Menu dropdown;
  
  private XulComponent parent;
  
  public SwtMenu(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menu");
    
    
    header = new MenuItem((Menu) parent.getManagedObject(), SWT.CASCADE);
    dropdown = new Menu((Shell) parent.getParent().getManagedObject(), SWT.DROP_DOWN);
    header.setMenu(dropdown);
    
    managedObject = menu;
    this.parent = parent;
  }
 

  @Override
  public XulComponent getParent() {
    return parent;
  }
  
  public void layout() {
    for (Element comp : getChildNodes()) {
      if (comp instanceof SwtMenupopup) {

        for (XulComponent compInner : ((SwtMenupopup) comp).getChildNodes()) {
          if (compInner instanceof XulMenuseparator) {
            MenuItem item = new MenuItem(dropdown, SWT.SEPARATOR);
            
          } else {

            MenuItem item = new MenuItem(dropdown, SWT.PUSH);
            item.setText(((SwtMenuitem) compInner).getLabel());
            
          }
        }
      }
    }
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
    header.setAccelerator(accessKey.charAt(0));
  }

  public void setDisabled(boolean disabled) {
    header.setEnabled(!disabled);
  }

  public void setLabel(String label) {
    header.setText(label);
  }

}

  