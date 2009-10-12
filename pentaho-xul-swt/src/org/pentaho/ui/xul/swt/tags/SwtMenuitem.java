package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtMenuitem extends SwtElement implements XulMenuitem{
  
  private static final Log logger = LogFactory.getLog(SwtMenuitem.class);
  private String onCommand;
  private boolean disabled = false;
  
  public SwtMenuitem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menuitem");
    setManagedObject("empty");
    
  }

  private String acceltext = "";
  private String accesskey = "";
  private String label = "";
  private String image = "";
  private boolean selected = false;
  
  public String getAcceltext() {
    return acceltext;
  }

  public String getAccesskey() {
    return accesskey;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public String getLabel() {
    return label;
  }

  public void setAcceltext(String accel) {
    this.acceltext = accel;
  }

  public void setAccesskey(String accessKey) {
    this.accesskey = accessKey;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = Boolean.parseBoolean(disabled);
  }
  
  public void setLabel(String label) {
    this.label = label;
  }

  public String getImage() {
    return image;
  }

  public boolean isSelected() {
    return selected;
  }
  
  public void setSelected(String selected){
    this.selected = Boolean.parseBoolean(selected);
  }
  
  public void setSelected(boolean val){
    selected = val;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getCommand() {
    return this.onCommand;  
  }

  public void setCommand(final String command) {
    this.onCommand = command;
    
  }
  
  public String toString(){
    return this.getLabel();
  }
  
}

  