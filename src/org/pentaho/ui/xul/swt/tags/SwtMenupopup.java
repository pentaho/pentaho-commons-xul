package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Menu;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtMenupopup extends SwingElement implements XulMenupopup{
  
  Menu menu = null;
  Combo menulist = null;
  
  public SwtMenupopup(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menupopup");
    children = new ArrayList<XulComponent>();
    managedObject = children;
   
    
  }

  public void layout(){
  }
}

  