package org.pentaho.ui.xul.swt.tags;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Direction;
import org.pentaho.ui.xul.util.SwtXulUtil;
import org.pentaho.ui.xul.util.XulUtil;

public class SwtButton extends SwtElement implements XulButton {
  private static final long serialVersionUID = -7218075117194366698L;
  private static final Log logger = LogFactory.getLog(SwtButton.class);

  protected org.eclipse.swt.widgets.Button button;
  private String label;
  private boolean disabled;
  private String image;
  private String disabledImage;
  private Direction dir;
  private Type type;
  private String group;
  private String onclick;
  private boolean selected;
  private XulComponent parent;
  private XulDomContainer domContainer;
  private Label imageButton;
  

  public SwtButton(Button button) {
    super("button");
    this.button = button;
    button.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent arg0){
        invoke(onclick);
      }
    });
    setManagedObject(this.button);
  }

  public SwtButton(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    this.parent = parent;
    this.domContainer = container;
    
    // Extract custom parameters
    if(self != null) {
      disabledImage = self.getAttributeValue("disabledimage"); //$NON-NLS-1$
    }
    
    // Special creation path for image buttons with no text. We don't want them to appear with the 
    // traditional button border.
    if(self != null && self.getAttributeValue("image") != null && self.getAttributeValue("label") == null){
      setManagedObject(createImageButton());
    } else {
      button = createNewButton((Composite)parent.getManagedObject());
      setManagedObject(button);
    }
  }
  
  private Control createImageButton(){
    imageButton = new Label(((Composite) parent.getManagedObject()), SWT.NONE);
    imageButton.addMouseListener(new MouseAdapter(){
      
      @Override
      public void mouseUp(MouseEvent arg0) {
        if(disabled == false){
          invoke(onclick);
        }
      }
      
    });
    imageButton.setCursor(new Cursor(((Composite) parent.getManagedObject()).getDisplay(), SWT.CURSOR_HAND));
    
    return imageButton;
    
  }
  
  public void setButton(Button button){
    this.button = button;
    button.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent arg0){
        invoke(onclick);
      }
    });
    setManagedObject(button);
    setVisible(isVisible());
  }
  
  protected Button createNewButton(Composite parent) {
    Button button = new Button(parent, SWT.NONE);
    button.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent arg0){
        invoke(onclick);
      }
    });
    return button;
  }

  
  /**
   * XUL's attribute is "disabled", thus this acts
   * exactly the opposite of SWT. If the property is not 
   * available, then the control is enabled. 
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if(button != null){
      button.setEnabled( !disabled );
    } else {
    	this.imageButton.setEnabled(!disabled);
    }

    if(this.disabled) {
      if(disabledImage != null) {
        displayImage(disabledImage);
      }
    } else {
      if(image != null) {
        displayImage(image);
      }
    }
  }

  public void setOnclick(final String method) {
    this.onclick = method;

  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
    button.setText(label);
  }

  public String getImage() {
    return this.image;
  }
  
  public String getDisabledImage() {
    return this.disabledImage;
  }

  public void setImage(String src) {
    this.image = src;
    displayImage(src);
  }
  
  public void setDisabledImage(String src) {
    this.disabledImage = src;   
  }

  public String getDir() {
    return dir.toString().toLowerCase();
  }

  public void setDir(String dir) {
    this.dir = Direction.valueOf(dir.toUpperCase());
  }

  public String getGroup() {
    return this.group;
  }

  public String getType() {
    return this.type.toString();
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public void setType(String type) {
    this.type = Type.valueOf(type.toString().toUpperCase());
    
  }

  public String getOnclick() {
    return this.onclick;
  }

  public void setSelected(String selected) {
    this.selected = Boolean.parseBoolean(selected);  
    button.setSelection(this.selected);
  }

  public boolean isSelected() {
    return selected;  
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    if(button != null){
      button.setSelection(this.selected);
    }
  }

  public void doClick() {
    if(button != null){
      button.setSelection(true);
    } else if(onclick != null){
      invoke(onclick);
    }
  }
  
  protected void displayImage(String src) {

    Display d = ((Composite) parent.getManagedObject()).getDisplay();
    if(d == null){
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }

    Image img = SwtXulUtil.getCachedImage(src, domContainer, d);
    if (img != null){
      if(button != null){
        button.setImage(img);
      } else { //image button implementation
        imageButton.setImage(img);
      }
    }
  }
  
}
