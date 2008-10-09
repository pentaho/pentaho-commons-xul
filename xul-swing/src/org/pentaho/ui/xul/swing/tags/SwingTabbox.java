package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;

import javax.swing.JTabbedPane;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingTabbox extends SwingElement implements XulTabbox {

  private JTabbedPane tabpane;

  private SwingTabpanels panels;

  private SwingTabs tabs;

  private int selectedIndex;

  public SwingTabbox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tabbox");
    tabpane = new JTabbedPane();
    this.managedObject = tabpane;
  }

  @Override
  public void addChild(Element ele) {
    super.addChild(ele);

    if (ele instanceof SwingTabs) {
      this.tabs = (SwingTabs) ele;
    } else if (ele instanceof SwingTabpanels) {
      this.panels = (SwingTabpanels) ele;
    }

  }

  public int getSelectedIndex() {
    return tabpane.getSelectedIndex();
  }

  public XulTabpanel getSelectedPanel() {
    return panels.getTabpanelByIndex(getSelectedIndex());
  }

  public XulTabpanels getTabpanels() {
    return panels;
  }

  public XulTabs getTabs() {
    return tabs;
  }

  public void setSelectedIndex(int index) {
    selectedIndex = index;
    if (tabpane.getTabCount() > 0) { // component instantiated
      tabpane.setSelectedIndex(selectedIndex);
    }
  }

  @Override
  public void layout() {
    tabpane.removeAll();
    for (int i = 0; i < tabs.getChildNodes().size(); i++) {
      XulTabpanel panel = this.panels.getTabpanelByIndex(i);
      if (panel == null) {
        //no panel for tab
        continue;
      }
      tabpane.add((Component) panel.getManagedObject(), tabs.getTabByIndex(i).getLabel());
      tabpane.setEnabledAt(i, !tabs.getTabByIndex(i).isDisabled());
    }
    tabpane.setSelectedIndex(selectedIndex);
    initialized = true;
  }

  @Override
  public void addComponentAt(XulComponent c, int pos) {
    super.addComponentAt(c, pos);
    if(initialized){
      resetContainer();
      layout();
    }
  }
  
  @Override
  public void addComponent(XulComponent c) {
    super.addComponent(c);
    if (initialized) {
      resetContainer();
      layout();
    }
  }

  public void setTabDisabledAt(boolean flag, int pos) {
    tabpane.setEnabledAt(pos, !flag);
  }

  public void removeTab(int idx) {
    if (tabpane.getTabCount() > idx) {
      tabpane.remove(idx);
    }
  }

  public void removeTabpanel(int idx) {
    if (tabpane.getTabCount() > idx) {
      tabpane.remove(idx);
    }
  }

  public void addTab(int idx) {
    
        // TODO Auto-generated method stub 
      
  }

  public void addTabpanel(int idx) {
    
        // TODO Auto-generated method stub 
      
  }
}
