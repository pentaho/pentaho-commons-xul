package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtTabbox extends SwtElement implements XulTabbox{

  
  private TabFolder tabFolder;
  private SwtTabpanels panels;
  private SwtTabs tabs;
  private int selectedIndex;
  
  public SwtTabbox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tabbox");
    tabFolder = new TabFolder ((Composite) parent.getManagedObject(), SWT.BORDER);
    this.managedObject = tabFolder;
  }
  

  @Override
  public void addChild(Element ele) {
     super.addChild(ele);
     
     if(ele instanceof SwtTabs){
       this.tabs = (SwtTabs) ele;
     } else if(ele instanceof SwtTabpanels){
       this.panels = (SwtTabpanels) ele;
     }
     
  }
  
  public int getSelectedIndex() {
    return tabFolder.getSelectionIndex();
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
    if(tabFolder.getItemCount() > 0){ // component instantiated
      tabFolder.setSelection(selectedIndex);
    }
  }

  @Override
  public void layout() {
    for(int i=0; i<tabs.getChildNodes().size(); i++){
      
      TabItem item = new TabItem (tabFolder, SWT.NONE);
      item.setText (tabs.getTabByIndex(i).getLabel());

      item.setControl ((Control) panels.getTabpanelByIndex(i).getManagedObject());
      tabFolder.getItem(i).getControl().setEnabled(!tabs.getTabByIndex(i).isDisabled());
      
    }
    tabFolder.setSelection(selectedIndex);
  }

  public void setTabDisabledAt(boolean flag, int pos) {
    tabFolder.getItem(pos).getControl().setEnabled(!flag);
  }


  public void removeTab(int idx) {
    if(tabFolder.getItemCount() >= idx){
      tabFolder.getItem(idx).dispose();
    }
  }


  public void removeTabpanel(int idx) {
    if(tabFolder.getItemCount() >= idx){
      tabFolder.getItem(idx).dispose();
    }
  }


  public void addTab(int idx) {
    TabItem item = new TabItem (tabFolder, SWT.NONE);
    item.setText (tabs.getTabByIndex(idx).getLabel());
    
    //may have been added after panel
    addTabpanel(idx);
  }


  public void addTabpanel(int idx) {
    
    //not sure if the tab has been added first, ignore if not
    if(tabFolder.getItemCount() <= idx){
      return;
    }
    TabItem item = tabFolder.getItem(idx);
    item.setControl ((Control) panels.getTabpanelByIndex(idx).getManagedObject());
    item.getControl().setEnabled(!tabs.getTabByIndex(idx).isDisabled());
    
  }

}

  