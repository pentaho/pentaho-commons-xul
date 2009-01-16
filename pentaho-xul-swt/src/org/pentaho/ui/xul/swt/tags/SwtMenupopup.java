package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Menu;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtMenupopup extends AbstractSwtXulContainer implements XulMenupopup{
  
  Menu menu = null;
  Combo menulist = null;
  XulComponent parent;
  public SwtMenupopup(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menupopup");
    this.parent = parent;
    
  }

  public void layout(){
  }

  @Override
  public XulComponent getParent() {
    return parent;
  }
  
  
}

  