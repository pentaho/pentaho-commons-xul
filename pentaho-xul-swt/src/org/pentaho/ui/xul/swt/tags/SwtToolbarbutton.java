package org.pentaho.ui.xul.swt.tags;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulToolbarbutton;
import org.pentaho.ui.xul.containers.XulToolbar;
import org.pentaho.ui.xul.containers.XulToolbar.ToolbarMode;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.XulUtil;

public class SwtToolbarbutton extends AbstractSwtXulContainer implements XulToolbarbutton{

  private ToolItem button;
  private boolean disabled;
  private String image;
  private String downImage;
  private XulComponent parent;
  private XulDomContainer container;
  private String command;
  private static final Log logger = LogFactory.getLog(SwtToolbarbutton.class);
  private XulToolbar parentToolbar;
  private String label;
  private String type;
  
  public SwtToolbarbutton(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("toolbarbutton");
    this.parent = parent;
    this.container = domContainer;
    if(parent instanceof XulToolbar){
      this.parentToolbar = (XulToolbar) parent;
    }
    
    if(parentToolbar != null){
      createButton(self, parent);
    }
  }
  
  private void createButton(Element self, XulComponent parent){
    
    if(button != null){
      button.dispose();
    }
    
    int style = SWT.RIGHT;
    if((type != null && type.equals("toggle")) || (self.getAttributeValue("type") != null && self.getAttributeValue("type").equals("toggle"))){
      style |= SWT.RADIO;
    } else {
      style |= SWT.PUSH;
    }
    
    button = new ToolItem((ToolBar) parent.getManagedObject(), style);
    
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
  

  public void setMenu(final Menu menu){
    //the generic impl... override if you need a more sophisticated handling of the menu
    if(button != null){
      button.addSelectionListener(new SelectionAdapter(){
  
        
        @Override
        public void widgetSelected(SelectionEvent evt) {
          Rectangle rect = button.getBounds();
          Point pt = button.getParent().toDisplay(new Point(rect.x, rect.y));
          menu.setLocation(pt.x, pt.y + rect.height);
          menu.setVisible(true);
          
        }
        
      });
    }
  
  }
  
  
  @Override
  public void layout() {
    if(parentToolbar == null){
      this.parentToolbar = (XulToolbar) getParent();
    }
    if(button == null){ // late instantiation
      createButton(this, getParent());
      setLabel(getLabel());
      setImage(getImage());
      setDownimage(getDownimage());
      setDisabled(isDisabled());
      setTooltiptext(getTooltiptext());
    }
    
  }

  public String getDownimage() {
    return downImage;
  }
  public String getDownimagedisabled() {
    // TODO Auto-generated method stub
    return null;
  }
  public void setDownimage(String img) {
    this.downImage = img;
    if(img == null || button == null){
      return;
    }
    Display d = ((Composite) parent.getManagedObject()).getDisplay();
    if(d == null){
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }
    

    try{
      InputStream in = XulUtil.loadResourceAsStream(img, container);
      
      button.setHotImage(new Image(((Composite) parent.getManagedObject()).getDisplay(), in));
        
    } catch (FileNotFoundException e){
      logger.error(e);
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
    return this.image;
  }
  public String getLabel() {
    // TODO Auto-generated method stub
    return null;
  }
  public String getOnclick() {
    return command;
  }
  public String getType() {
    return type;
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
  
  public void setImageFromStream(InputStream in){

    Display d = ((Composite) getParent().getManagedObject()).getDisplay();
    if(d == null){
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }
    button.setImage(new Image(d, in));
    
  }
  public void setImage(String src) {
    this.image = src;
    if(src == null){
      return;
    }
    if(button == null){
      return;
    }
    if(!(parentToolbar.getMode().equals("ICONS")|| parentToolbar.getMode().equals("FULL"))){
      return;
    }
    Display d = ((Composite) parentToolbar.getManagedObject()).getDisplay();
    if(d == null){
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }

    try{
      InputStream in = XulUtil.loadResourceAsStream(src, container);
      
      button.setImage(new Image(d, in));
        
    } catch (FileNotFoundException e){
      logger.error(e);
    }
    
    
  }
  public void setLabel(String label) {
    this.label = label;
    if(button == null){
      return;
    }
    
    if(parentToolbar.getMode().equals("ICONS")){
      return;
    }
    this.button.setText(label);
    
  }
  public void setOnclick(String method) {
    command = method;
    
  }
  public void setSelected(boolean selected) {
    if(button != null){
      button.setSelection(selected);
    }
  }
  public void setSelected(String selected) {
    // TODO Auto-generated method stub
    
  }
  public void setType(String type) {
    this.type = type;
    if(this.initialized){ // runtime change
      setTooltiptext(getTooltiptext());
      this.setDisabled(isDisabled());
      this.setImage(this.getImage());
      this.setDownimage(getDownimage());
      this.setDownimagedisabled(getDownimagedisabled());
      this.setLabel(getLabel());
      this.setOnclick(getOnclick());
      
    }
  }

  @Override
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    if(button == null){
      return;
    }
    button.setToolTipText(tooltip);
  }
  
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if(button != null){
      button.setEnabled( !disabled );
    } 
  }
  
  public boolean isDisabled() {
    return disabled;
  }

  
}
