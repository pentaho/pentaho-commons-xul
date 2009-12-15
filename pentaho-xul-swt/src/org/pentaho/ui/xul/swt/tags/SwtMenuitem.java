package org.pentaho.ui.xul.swt.tags;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.XulUtil;

public class SwtMenuitem extends SwtElement implements XulMenuitem{
  
  private static final Log logger = LogFactory.getLog(SwtMenuitem.class);
  private String onCommand;
  private boolean disabled = false;
  private MenuItem item;
  private XulDomContainer domContainer;
  private XulComponent parent;
  
  
  public SwtMenuitem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menuitem");
    this.parent = parent;
    setManagedObject("empty");
    this.domContainer = domContainer;
    if(parent.getManagedObject() != null && parent.getManagedObject() instanceof Menu){
      createItem(self, parent, -1);
      
    }

  }
  
  public SwtMenuitem(XulComponent parent, XulDomContainer domContainer, String tagName, int pos) {
    super("menuitem");
    this.parent = parent;
    setManagedObject("empty");
    this.domContainer = domContainer;
    if(parent.getManagedObject() != null && parent.getManagedObject() instanceof Menu){
     createItem(null, parent, pos);
    }

  }
  
  private void createItem(Element self, XulComponent parent, int pos){
    int style = SWT.PUSH;
    if(self != null && self.getAttributeValue("type") != null && self.getAttributeValue("type").equals("checkbox")){
      style = SWT.CHECK;
    }

    if(pos > -1){
      item = new MenuItem((Menu) parent.getManagedObject(), style, pos);
    } else {
      item = new MenuItem((Menu) parent.getManagedObject(), style);
    }
    
    item.addSelectionListener(new SelectionAdapter(){
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        String command = SwtMenuitem.this.onCommand;
        if(command != null){
          invoke(command);
        }
      }
    });

    setManagedObject(item);
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
    if (item != null) {
      item.setEnabled(!disabled);
    }
  }

  public void setDisabled(String disabled) {
    setDisabled(Boolean.parseBoolean(disabled));
  }
  
  public void setLabel(String label) {
    this.label = label;
    setText();
  }

  public String getImage() {
    return image;
  }

  public boolean isSelected() {
    return (item != null)? item.getSelection() : selected;
  }
  
  public void setSelected(String selected){
    this.selected = Boolean.parseBoolean(selected);
    if(item != null){
      item.setSelection(this.selected);
    }
  }
  
  public void setSelected(boolean val){
    selected = val;
  }

  public void setImage(String image) {
    this.image = image;
    if(StringUtils.isNotEmpty(image)){
      try {
        InputStream in = XulUtil.loadResourceAsStream(image, domContainer);
        Image img = new Image(item.getDisplay(), in);
        
        int pixelIndex = img.getImageData().palette.getPixel(new RGB(255, 255, 255));
        
        img.getImageData().transparentPixel = pixelIndex;
        Image tempImage = new Image(item.getDisplay(), img.getImageData());
        img.dispose();
        img = tempImage;
        item.setImage(img);

      } catch (FileNotFoundException e) {
        logger.warn(e);
      }
    }
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
  
  public void reposition(int position){
    int accel = item.getAccelerator();
    item.dispose();
    createItem(this, parent, position);
    setDisabled(isDisabled());
    this.setImage(getImage());
    setSelected(isSelected());
    item.setAccelerator(accel);
    setAcceltext(getAcceltext());
  }
  
}

  