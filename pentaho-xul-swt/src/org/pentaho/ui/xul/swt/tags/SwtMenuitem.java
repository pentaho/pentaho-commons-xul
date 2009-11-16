package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtMenuitem extends SwtElement implements XulMenuitem{
  
  private static final Log logger = LogFactory.getLog(SwtMenuitem.class);
  private String onCommand;
  private boolean disabled = false;
  private MenuItem item;
  
  public SwtMenuitem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menuitem");
    setManagedObject("empty");
    if(parent.getManagedObject() != null){
      item = new MenuItem((Menu) parent.getManagedObject(), SWT.PUSH);
      item.addSelectionListener(new SelectionAdapter(){
        @Override
        public void widgetSelected(SelectionEvent arg0) {
          String command = SwtMenuitem.this.onCommand;
          if(command != null){
            invoke(command);
          }
        }
      });
    }

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
    setText();
  }
  
  private void setText(){
    if(item != null){
      String text = "";
      if(this.label != null){
        text += this.label;
      }
      text += "\t"+acceltext;
      item.setText(text);
    }
  }

  public void setAccesskey(String accessKey) {
    
    if(item != null){
      int mask = 0;
      if(accessKey.indexOf("ctrl") > -1){
        mask += SWT.CTRL;
      }
      if(accessKey.indexOf("shift") > -1){
        mask += SWT.SHIFT;
      }
      if(accessKey.indexOf("alt") > -1){
        mask += SWT.ALT;
      }
      String remainder = accessKey.replaceAll("ctrl", "").replaceAll("shift", "").replaceAll("alt", "").replaceAll("-", "").trim();
      if(remainder.length() == 1){
        mask += remainder.toUpperCase().charAt(0);
      }
      
      item.setAccelerator(mask);
    }
  }
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = Boolean.parseBoolean(disabled);
  }
  
  public void setLabel(String label) {
    this.label = label;
    setText();
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

  