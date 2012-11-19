package org.pentaho.ui.xul.jface.tags;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.XulUtil;

public class JfaceMenuitem extends SwtElement implements XulMenuitem{
  
	private static int menuItemSerial = 0;
  private static final Log logger = LogFactory.getLog(JfaceMenuitem.class);
  private String onCommand;
  private boolean disabled = false;
  private XulDomContainer domContainer;
  private XulComponent parent;
  private String remainder;
  private Action action;
  private String acceltext = "";
  private String accesskey = "";
  private String label = "";
  private String image = "";
  private boolean selected = false;
  
  
  public JfaceMenuitem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menuitem");
    this.parent = parent;
//    setManagedObject("empty");
    this.domContainer = domContainer;
    if(parent.getManagedObject() != null && parent.getManagedObject() instanceof IMenuManager){
      createItem(self, parent, -1, false);
    } else {
//System.out.println("JfaceMenuitem not creating item");    	
    }

  }

  public JfaceMenuitem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName, int pos) {
    super("menuitem");
    this.parent = parent;
//    setManagedObject("empty");
    this.domContainer = domContainer;
    if(parent.getManagedObject() != null && parent.getManagedObject() instanceof IMenuManager){
     createItem(self, parent, pos, true);
    } else {
//System.out.println("JfaceMenuitem not creating item");    	
    }

  }
  
  public IAction getAction() {
	  return action;
  }
  
  public void createItem(Element self, XulComponent parent, int pos, boolean autoAdd){

	  int style = Action.AS_DROP_DOWN_MENU;
	  if( self != null && "checkbox".equals(self.getAttributeValue("type")) ) {
		style = Action.AS_CHECK_BOX;
	  }
	  
    action = new Action((self != null) ? self.getAttributeValue("label") : "tmp name", style) {
    	      public void run() {
    	          String command = JfaceMenuitem.this.onCommand;
    	          if(command != null){
    	        	  try {
    	            invoke(command);
    	        	  } catch (Exception e) {
    	        		  e.printStackTrace();
    	        	  }
    	          }
    	      }
    	    };
    	    
    	    
    	    String id = getId();
    	    if( id == null && self != null ) {
    	    	id = self.getAttributeValue("ID");
    	    }
    	    if( id == null ) {
    	    	id="menuitem-"+menuItemSerial;
    	    	menuItemSerial++;
    	    }
    	    action.setId(id);
    	    if( action.getText() == null || action.getText().equals("") ) {
//    	    	System.out.println("JfaceMenuitem createItem adding blank action");
    	    }
    	    
    	    action.setChecked(selected);
    	    
    setManagedObject(action);
    if( autoAdd ) {
  	  IMenuManager menu = (IMenuManager) parent.getManagedObject();
  	  if( pos == -1 ) {
  		  menu.add(action);
  	  } else {
  	    if( pos < getChildNodes().size() ) {
	    	if( pos >= menu.getItems().length ) {
//	    		System.out.println("hmm..."+menu.getItems().length);
	    	}
	   		String anchorId = menu.getItems()[pos].getId();
	   		if( anchorId == null ) {
//	   			System.out.println("can't find anchor id for "+((JfaceMenuitem) self).getLabel());
		    	menu.add(action);
		    	parent.addChild(this);
	   		} else {
	   			menu.insertBefore(anchorId, action);
	   			parent.addChildAt(this, pos);
	   		}
	    } else {
	    	parent.addChild(this);
	    	//menu.add(action); -- The menu action is already added by parent.addChild(this)
	    }
  	  }
    }
  }

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
	  
    if(action != null){
      String text = "";
      if(this.label != null){
        text += this.label;
      }
      if( !acceltext.isEmpty() ) {
          text += "\t"+acceltext;
      }

      action.setText(text);
    }
  }

  public void setAccesskey(String accessKey) {
    
    if(action != null){
      int mask = 0;
      if(accessKey.indexOf("ctrl") > -1){ //$NON-NLS-1$
    	boolean isMac = System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0;
        mask += isMac ? SWT.COMMAND : SWT.CTRL;
      }
      if(accessKey.indexOf("shift") > -1){ //$NON-NLS-1$
        mask += SWT.SHIFT;
      }
      if(accessKey.indexOf("alt") > -1){ //$NON-NLS-1$
        mask += SWT.ALT;
      }
      if(accessKey.indexOf("pageup") > -1) { //$NON-NLS-1$
        mask += SWT.PAGE_UP;
      }
      if(accessKey.indexOf("pagedown") > -1) { //$NON-NLS-1$
        mask += SWT.PAGE_DOWN;
      }
      if(accessKey.indexOf("home") > -1) { //$NON-NLS-1$
        mask += SWT.HOME;
      }
      if (accessKey.indexOf("esc") > -1) { //$NON-NLS-1$
        mask += SWT.ESC;
      }
      remainder = accessKey.replaceAll("ctrl", "").replaceAll("shift", "").replaceAll("alt", "").replaceAll("-", "").trim();
      if(remainder.length() == 1){
        mask += remainder.toUpperCase().charAt(0);
      } else if (remainder.length() > 1 && remainder.startsWith("f")){ //$NON-NLS-1$
        // function key
        mask += LegacyActionTools.findKeyCode(remainder);        
      }
      
      action.setAccelerator(mask);
    }
  }
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (action != null) {
    	action.setEnabled(!disabled);
    }
  }
  
  public void setLabel(String label) {
    this.label = label;
    setText();
  }

  public String getImage() {
    return image;
  }

  public boolean isSelected() {
	  if( action != null ) {
//	    return action.isChecked();
	  }
	  return selected;
  }
  
  public void setSelected(boolean val){
	  boolean changing = selected != val;
	  selected = val;
    if(action != null){
      action.setChecked(selected);
    }
    if(parent != null && parent instanceof JfaceMenupopup && parent.getParent() instanceof JfaceMenuList ) {
    	if( val ) {
        	((JfaceMenuList) parent.getParent() ).setSelectedItem(this);
    	} 
    	else if(changing) {
        	((JfaceMenuList) parent.getParent() ).setSelectedItem(null);
    	}
    }
  }

  public void setImage( Image img ) {
      ImageDescriptor id = ImageDescriptor.createFromImage(img);

      if( action != null ) {
      	action.setImageDescriptor(id);
      }
  }
  
  public void setImage(String image) {
    this.image = image;
    if(StringUtils.isNotEmpty(image)){
      InputStream in = null;
      try {
        in = XulUtil.loadResourceAsStream(image, domContainer);
        Image img = new Image(Display.getCurrent(), in);
        ImageDescriptor id = ImageDescriptor.createFromImage(img);

        if( action != null ) {
        	action.setImageDescriptor(id);
        }

      } catch (FileNotFoundException e) {
        logger.warn(e);
      } finally {
        try{
          if(in != null){
            in.close();
          }
        } catch(IOException ignored){}
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
//    int accel = item..getAccelerator();
    action = null;
    createItem(this, parent, position, true);
    setDisabled(isDisabled());
    this.setImage(getImage());
    setSelected(isSelected());
//    item.setAccelerator(accel);
    setAcceltext(getAcceltext());
  }
  
  @Override
  public void setVisible(boolean visible) {

	  this.visible = visible;
	  if( parent.getManagedObject() instanceof IMenuManager ) {
		  IMenuManager menu = (IMenuManager) parent.getManagedObject();
		  for( IContributionItem item : menu.getItems() ) {
			  if( item.getId() != null && item.getId().equals(action.getId() )) {
	  			  item.setVisible(visible);
	  			  return;
			  }
		  }
	  }
	  
  }
  
  
}

  