package org.pentaho.ui.xul.swt.tags;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtTabs  extends SwtElement implements XulTabs{
  public SwtTabs(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tabs");
    this.managedObject = "empty";
  }
  
  public XulTab getTabByIndex(int index){
    return (SwtTab) this.getChildNodes().get(index);
  }
  
  @Override
  public void layout() {
  }
  

  @Override
  public void removeChild(Element ele) {
    ((XulTabbox) getParent()).removeTab(this.getChildNodes().indexOf(ele));
  }

  public int getTabCount() {
    return this.getChildNodes().size();
  }
}
