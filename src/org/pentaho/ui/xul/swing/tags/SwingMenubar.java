package org.pentaho.ui.xul.swing.tags;

import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenubar extends SwingElement implements XulMenubar{

  private JMenuBar menuBar;
  public SwingMenubar(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menubar");
    
    children = new ArrayList<XulComponent>();
    
    menuBar = new JMenuBar();
    managedObject = menuBar;
    
  }
  
  @Override
  public void layout(){
    for(XulComponent comp : children){
      if(comp instanceof SwingMenu){
        this.menuBar.add((JMenu) ((SwingMenu) comp).getManagedObject());
      }
    }
  }
  
}

  