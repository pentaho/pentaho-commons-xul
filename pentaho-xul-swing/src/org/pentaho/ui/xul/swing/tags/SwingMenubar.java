package org.pentaho.ui.xul.swing.tags;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenubar extends AbstractSwingContainer implements XulMenubar {

  private JMenuBar menuBar;

  public SwingMenubar(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menubar");

    menuBar = new JMenuBar();
    menuBar.setMinimumSize(new Dimension(20, 20));
    setManagedObject(menuBar);

  }

  @Override
  public void layout() {
    for (Element comp : getChildNodes()) {
      if (comp instanceof SwingMenu) {
        this.menuBar.add((JMenu) ((SwingMenu) comp).getManagedObject());
      }
    }
    initialized = true;
  }


}
