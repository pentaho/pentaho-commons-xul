package org.pentaho.ui.xul.swing.tags;

import javax.swing.JButton;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.Element;

public class SwingMenuseparator extends SwingElement implements XulMenuseparator{
  
  public SwingMenuseparator(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menuseparator");
    this.managedObject = "Separator";
  }

}

  