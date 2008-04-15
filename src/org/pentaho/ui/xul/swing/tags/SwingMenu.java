package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenu extends SwingElement implements XulMenu{

  
  private JMenu menu;
  public SwingMenu(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menu");
    
    children = new ArrayList<XulComponent>();
    
    menu = new JMenu();
    managedObject = menu;
    
  }
  

  public void layout(){
    for(XulComponent comp : children){
      if(comp instanceof SwingMenupopup){

        for(XulComponent compInner : ((SwingMenupopup) comp).getChildNodes()){
          if(compInner instanceof XulMenuseparator){
            this.menu.addSeparator();
          } else {
            this.menu.add((JMenuItem) ((SwingMenuitem) compInner).getManagedObject());
          }
        }
      }
    }
  }
  
  
  
  @Override
  public String getAcceltext() {
    return String.valueOf(menu.getAccelerator().getKeyChar());
  }

  @Override
  public String getAccesskey() {
    return String.valueOf(menu.getText().charAt(menu.getDisplayedMnemonicIndex()));
  }

  @Override
  public boolean getDisabled() {
    return menu.isEnabled();
  }

  @Override
  public String getLabel() {
    return menu.getText();
  }

  @Override
  public void setAcceltext(String accel) {
    menu.setAccelerator(KeyStroke.getKeyStroke(accel));
  }

  @Override
  public void setAccesskey(String accessKey) {
    menu.setMnemonic(accessKey.charAt(0));
  }

  @Override
  public void setDisabled(boolean disabled) {
    menu.setEnabled(disabled);
  }

  @Override
  public void setLabel(String label) {
    menu.setText(label);
  }
  
}

  