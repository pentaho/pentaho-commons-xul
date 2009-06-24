package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtTabbox extends AbstractGwtXulContainer implements XulTabbox {

  static final String ELEMENT_NAME = "tabbox"; //$NON-NLS-1$

  private TabPanel tabPanel;

  private GwtTabPanels panels;

  private GwtTabs tabs;

  private int selectedIndex;

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTabbox();
      }
    });
  }

  public GwtTabbox() {
    this(Orient.HORIZONTAL);
  }

  public GwtTabbox(Orient orient) {
    super(ELEMENT_NAME);
    managedObject = tabPanel = new TabPanel();

/*    tabpanel.addTabListener(new TabListener() {

      @Override
      public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
        final String onClickMethod = tabs.getTabByIndex(tabIndex).getOnclick();
        try {
          setSelectedIndex(tabIndex);
          GwtTabbox.this.getXulDomContainer().invoke(onClickMethod, new Object[] {});
        } catch (XulException e) {
          e.printStackTrace();
        }
      }

    });*/
  }
  
  @Override
  public void addChild(Element ele) {
    super.addChild(ele);

    if (ele instanceof GwtTabs) {
      this.tabs = (GwtTabs) ele;
    } else if (ele instanceof GwtTabPanels) {
      this.panels = (GwtTabPanels) ele;
    }

  }

  public int getSelectedIndex() {
    return selectedIndex;
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
    int previousValue = selectedIndex;
    selectedIndex = index;
    if (tabPanel.getWidgetCount() > 0) { // component instantiated
      tabPanel.selectTab(index);
      firePropertyChange("selectedIndex", previousValue, index);
    }
  }

  @Override
  public void layout() {
    for (int i = 0; i < tabPanel.getWidgetCount(); i++) {
      tabPanel.remove(i);
    }

    for (int i = 0; i < tabs.getChildNodes().size(); i++) {
      XulTabpanel panel = this.panels.getTabpanelByIndex(i);
      if (panel == null) {
        //no panel for tab
        continue;
      }
      tabPanel.add((Widget) panel.getManagedObject(), tabs.getTabByIndex(i).getLabel());
    }
    setSelectedIndex(selectedIndex);
    initialized = true;
    tabPanel.addSelectionHandler(new SelectionHandler<Integer>(){

      @Override
      public void onSelection(SelectionEvent<Integer> event) {
          if(event != null && event.getSelectedItem() >= 0) {
            try {
              final String onClickMethod = tabs.getTabByIndex(event.getSelectedItem()).getOnclick();
              GwtTabbox.this.getXulDomContainer().invoke(onClickMethod, new Object[] {});
            } catch (XulException e) {
              e.printStackTrace();
            }
          }
      }
      
    });
  }

  public void setTabDisabledAt(boolean flag, int pos) {

  }

  public void removeTab(int idx) {
    if (tabPanel.getWidgetCount() > idx) {
      tabPanel.remove(idx);
    }
  }

  public void removeTabpanel(int idx) {
    if (tabPanel.getWidgetCount() > idx) {
      tabPanel.remove(idx);
    }
  }

  public void addTab(int idx) {
  }

  public void addTabpanel(int idx) {
  }

}
