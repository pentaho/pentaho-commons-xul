package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

public class GwtTabs extends AbstractGwtXulContainer implements XulTabs {
  static final String ELEMENT_NAME = "tabs"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTabs();
      }
    });
  }

  public GwtTabs() {
    super(ELEMENT_NAME);
    setManagedObject("empty");
  }
 
  public XulTab getTabByIndex(int index) {
    return (GwtTab) this.getChildNodes().get(index);
  }
  
  @Override
  public void addChild(Element comp){
    super.addChild(comp);
    if(initialized){
      ((GwtTabbox) getParent()).layout();
    }
  }

  @Override
  public void addChildAt(Element comp, int pos){
    super.addChildAt(comp, pos);
    if(initialized){
      ((GwtTabbox) getParent()).layout();
    }
  }
  
  public int getTabCount() {
    return this.getChildNodes().size();
  }
  
}
