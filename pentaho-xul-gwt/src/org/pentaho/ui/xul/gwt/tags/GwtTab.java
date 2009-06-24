package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTab extends AbstractGwtXulContainer implements XulTab { 
  private String label;
  private boolean disabled = false;
  private String onclick;
  private XulTabbox tabbox;

  static final String ELEMENT_NAME = "tab"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTab();
      }
    });
  }

  public GwtTab() {
    super(ELEMENT_NAME);
    managedObject = "empty";
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setOnclick(srcEle.getAttribute("onclick"));
  }
  public boolean isDisabled() {
    return disabled;
  }

  public String getLabel() {
    return label;
  }

  public String getOnclick() {
    return onclick;
  }

  public void getTabbox(){
    if(tabbox == null){
      if(getParent() != null){
        tabbox = (XulTabbox) getParent().getParent();
      }
    }
  }
  
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    getTabbox();
    if(tabbox != null){
      tabbox.setTabDisabledAt(disabled, getParent().getChildNodes().indexOf(this));
    }
    
  }

  public void setLabel(String label) {
    this.label = label;
    
  }

  public void setOnclick(String onClick) {
    this.onclick = onClick;
    
  }
  @Override
  public void layout() {
  }

}
