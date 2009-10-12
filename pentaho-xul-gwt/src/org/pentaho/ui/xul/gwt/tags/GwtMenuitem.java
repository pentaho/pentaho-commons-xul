package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;

public class GwtMenuitem extends AbstractGwtXulComponent implements XulMenuitem {
  
  static final String ELEMENT_NAME = "menuitem"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenuitem();
      }
    });
  }
  
  private Label label;
  private String image;
  private String onCommand;
  private MenuItem menuitem;
  private boolean isSelected;
  
  public GwtMenuitem() {
    super(ELEMENT_NAME);
    menuitem = new MenuItem("blank", (Command) null);
    setManagedObject(menuitem);
  }
  
  public GwtMenuitem(XulMenupopup popup){
    this();
    
  }
  

  public String getAcceltext() {
    return null;
  }

  public String getAccesskey() {
    return null;
  }

  public boolean isDisabled() {
    return false;
  }

  public String getLabel() {
    return menuitem.getText();
  }

  public void setAcceltext(String accel) {
    throw new RuntimeException("Not implemented");
  }

  public void setAccesskey(String accessKey) {
    throw new RuntimeException("Not implemented");
  }

  public void setDisabled(boolean disabled) {
    throw new RuntimeException("Not implemented");
  }

  public void setDisabled(String disabled) {
    throw new RuntimeException("Not implemented");
  }

  public void setLabel(String label) {
    menuitem.setText(label);
  }

  public String getImage() {
      return image;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    this.isSelected = selected;
  }
  
  public void setImage(String image) {
    this.image = image;
  }

  public String getCommand() {
    return this.onCommand;  
  }

  public void setCommand(final String command) {
    this.onCommand = command;
    menuitem.setCommand(new Command(){
      public void execute() {
        invoke(command);
      }
    }); 
  }
  
  @Override
  public String toString(){
    return getLabel();
  }

  
}
