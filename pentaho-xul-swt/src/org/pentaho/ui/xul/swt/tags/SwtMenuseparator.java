package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtMenuseparator extends SwtElement implements XulMenuseparator{
  public SwtMenuseparator(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menuseparator");

    
    MenuItem item = new MenuItem((Menu) parent.getManagedObject() , SWT.SEPARATOR);
  }
  
}
