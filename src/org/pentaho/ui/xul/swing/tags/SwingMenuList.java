package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenuList extends SwingElement implements XulMenuList{

  
  private JComboBox combobox;
  public SwingMenuList(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menulist");
    
    children = new ArrayList<XulComponent>();
    
    combobox = new JComboBox();
    managedObject = combobox;
    
  }
  

  public void layout() {
		for (XulComponent comp : children) {
			if (comp instanceof SwingMenupopup) {

				for (XulComponent compInner : ((SwingMenupopup) comp)
						.getChildNodes()) {
					JMenuItem jmenuItem = (JMenuItem) ((SwingMenuitem) compInner)
					.getManagedObject();
					this.combobox.add(jmenuItem);
				}
			}
		}
	}
}

  