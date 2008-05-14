package org.pentaho.ui.xul.swing.tags;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenuList extends SwingElement implements XulMenuList {

  private JComboBox combobox;

  private XulDomContainer xulDomContainer;

  public SwingMenuList(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menulist");

    this.xulDomContainer = xulDomContainer;

    children = new ArrayList<XulComponent>();

    combobox = new JComboBox(new DefaultComboBoxModel());
    managedObject = combobox;

  }

  public void layout() {
    SwingMenupopup popup = getPopupElement();
    DefaultComboBoxModel model = (DefaultComboBoxModel) this.combobox.getModel();
    model.removeAllElements();
    
    System.err.println(popup.getChildNodes());
    for (XulComponent item : popup.getChildNodes()) {
      JMenuItem jmenuItem = (JMenuItem) ((SwingMenuitem) item).getManagedObject();
      System.err.println("adding to combobox: " + jmenuItem.getText());
      model.addElement(jmenuItem.getText());
    }
  }

  public SwingMenupopup getPopupElement() {
    for (XulComponent comp : children) {
      if (SwingMenupopup.class.isAssignableFrom(comp.getClass())) {
        return (SwingMenupopup) comp;
      }
    }
    throw new IllegalStateException("menulist is missing a menupopup child element");
  }

  /*
   * Swaps out the managed list.  Effectively replaces the SwingMenupopup child component.
   * (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulMenuList#replaceAll(java.util.List)
   */
  public void replaceAllItems(List<String> objects) throws XulDomException {
    XulMenupopup popup = getPopupElement();
    for (XulComponent menuItem : popup.getChildNodes()) {
      popup.removeChild(menuItem);
    }

    for(String s : objects) {
      SwingMenuitem item = new SwingMenuitem(popup, this.xulDomContainer, null);
      item.setLabel(s);
      popup.addChild(item);
    }

    layout();
  }
}
