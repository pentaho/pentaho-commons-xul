package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenu extends SwingElement implements XulMenu {

  private JMenu menu;

  public SwingMenu(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menu");

    children = new ArrayList<XulComponent>();

    menu = new JMenu();
    managedObject = menu;

  }

  public void layout() {
    for (XulComponent comp : children) {
      if (comp instanceof SwingMenupopup) {

        for (XulComponent compInner : ((SwingMenupopup) comp).getChildNodes()) {
          if (compInner instanceof XulMenuseparator) {
            this.menu.addSeparator();
          } else {
            this.menu.add((JMenuItem) ((SwingMenuitem) compInner).getManagedObject());
          }
        }
      }
    }
    initialized = true;
  }

  @Override
  public void addComponent(XulComponent c) {
    super.addComponent(c);
    if (initialized) {
      resetContainer();
      layout();
    }
  }

  public String getAcceltext() {
    return String.valueOf(menu.getAccelerator().getKeyChar());
  }

  public String getAccesskey() {
    return String.valueOf(menu.getText().charAt(menu.getDisplayedMnemonicIndex()));
  }

  public boolean getDisabled() {
    return menu.isEnabled();
  }

  public String getLabel() {
    return menu.getText();
  }

  public void setAcceltext(String accel) {
    menu.setAccelerator(KeyStroke.getKeyStroke(accel));
  }

  public void setAccesskey(String accessKey) {
    menu.setMnemonic(accessKey.charAt(0));
  }

  public void setDisabled(boolean disabled) {
    menu.setEnabled(disabled);
  }

  public void setLabel(String label) {
    menu.setText(label);
  }

}
