package org.pentaho.ui.xul.swt.tags;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulToolbarbutton;
import org.pentaho.ui.xul.containers.XulToolbar;
import org.pentaho.ui.xul.containers.XulToolbar.ToolbarMode;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtToolbarbutton extends AbstractSwtXulContainer implements XulToolbarbutton{

  private ToolItem button;
  private String image;
  private String downImage;
  private XulComponent parent;
  private XulDomContainer container;
  private String command;
  private static final Log logger = LogFactory.getLog(SwtToolbarbutton.class);
  private XulToolbar parentToolbar;
  
  public SwtToolbarbutton(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("toolbarbutton");
    this.parent = parent;
    this.container = domContainer;
    this.parentToolbar = (XulToolbar) parent;
    
    button = new ToolItem((ToolBar) parent.getManagedObject(), SWT.PUSH | SWT.RIGHT);
    button.addSelectionListener(new SelectionAdapter(){

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        if(command != null){
          invoke(command);
        }
      }
      
    });
    
    setManagedObject(button);
  }
  
  public String getDownimage() {
    // TODO Auto-generated method stub
    return null;
  }
  public String getDownimagedisabled() {
    // TODO Auto-generated method stub
    return null;
  }
  public void setDownimage(String img) {
    this.downImage = img;   
    Display d = ((Composite) parent.getManagedObject()).getDisplay();
    if(d == null){
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }
    
    InputStream in = SwtButton.class.getClassLoader().getResourceAsStream(this.container.getXulLoader().getRootDir()+img);
    if(in == null){
      File i = new File(this.container.getXulLoader().getRootDir()+img);
      if(i.exists()){
        try {
          in = new FileInputStream(i);
        } catch (FileNotFoundException e) {
          logger.warn("could not find image: "+img);
          return;
        }
      }
    }
    if(in != null){
      button.setHotImage(new Image(((Composite) parent.getManagedObject()).getDisplay(), in));
    }
  }
  public void setDownimagedisabled(String img) {
    // TODO Auto-generated method stub
    
  }
  public void setSelected(boolean selected, boolean fireEvent) {
    // TODO Auto-generated method stub
    
  }
  public void doClick() {
    // TODO Auto-generated method stub
    
  }
  public String getDir() {
    // TODO Auto-generated method stub
    return null;
  }
  public String getGroup() {
    // TODO Auto-generated method stub
    return null;
  }
  public String getImage() {
    // TODO Auto-generated method stub
    return null;
  }
  public String getLabel() {
    // TODO Auto-generated method stub
    return null;
  }
  public String getOnclick() {
    return command;
  }
  public String getType() {
    // TODO Auto-generated method stub
    return null;
  }
  public boolean isSelected() {
    // TODO Auto-generated method stub
    return false;
  }
  public void setDir(String dir) {
    // TODO Auto-generated method stub
    
  }
  public void setGroup(String group) {
    // TODO Auto-generated method stub
    
  }
  public void setImage(String src) {
    if(!(parentToolbar.getMode().equals("ICONS")|| parentToolbar.getMode().equals("FULL"))){
      return;
    }
    this.image = src;   
    Display d = ((Composite) parent.getManagedObject()).getDisplay();
    if(d == null){
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }
    
    InputStream in = SwtButton.class.getClassLoader().getResourceAsStream(this.container.getXulLoader().getRootDir()+src);
    if(in == null){
      File img = new File(this.container.getXulLoader().getRootDir()+src);
      if(img.exists()){
        try {
          in = new FileInputStream(img);
        } catch (FileNotFoundException e) {
          logger.warn("could not find image: "+src);
          return;
        }
      }
    }
    if(in != null){
      button.setImage(new Image(((Composite) parent.getManagedObject()).getDisplay(), in));
    }
  }
  public void setLabel(String label) {
    if(parentToolbar.getMode().equals("ICONS")){
      return;
    }
    this.button.setText(label);
    
  }
  public void setOnclick(String method) {
    command = method;
    
  }
  public void setSelected(boolean selected) {
    // TODO Auto-generated method stub
    
  }
  public void setSelected(String selected) {
    // TODO Auto-generated method stub
    
  }
  public void setType(String type) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    button.setToolTipText(tooltip);
  }
  
  
  
}
