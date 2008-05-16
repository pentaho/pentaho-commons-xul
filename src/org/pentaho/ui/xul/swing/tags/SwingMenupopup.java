package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.Element;

public class SwingMenupopup extends SwingElement implements XulMenupopup{
  
  public SwingMenupopup(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menupopup");
    
    children = new ArrayList<XulComponent>();
    
    managedObject = children;
    
  }
  

  public void layout(){
    
  }
}

  