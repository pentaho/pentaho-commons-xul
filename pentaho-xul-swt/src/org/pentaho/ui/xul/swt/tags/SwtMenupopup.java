package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtMenupopup extends AbstractSwtXulContainer implements XulMenupopup{
  
  Menu menu = null;
  Combo menulist = null;
  XulComponent parent;
  public SwtMenupopup(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menupopup");
    this.parent = parent;
    if(parent.getManagedObject() instanceof MenuItem){
      Shell shell = null;
 
      // only build if a child of a top level menu, otherwise it will be build recursively by a parent
      if (shell == null){
        XulComponent p = parent;
        
        while(p != null && p instanceof XulRoot == false){
          if(p instanceof XulMenubar && p.getAttributeValue("parenttoouter") != null 
              && p.getAttributeValue("parenttoouter").equals("true") 
              && domContainer.getOuterContext() != null){
            shell = (Shell) domContainer.getOuterContext();
            break;
          }
          p = p.getParent();
        }
        if(p != null && p instanceof XulRoot){
          shell = (Shell) p.getManagedObject();
        }
      }

      Menu flyout = new Menu(shell, SWT.DROP_DOWN);
      ((MenuItem) parent.getManagedObject()).setMenu(flyout);
      menu = flyout;
      setManagedObject(flyout);
      
    } else {
      
    }
  }

  public void layout(){
  }

  @Override
  public XulComponent getParent() {
    return parent;
  }
  
  
}

  