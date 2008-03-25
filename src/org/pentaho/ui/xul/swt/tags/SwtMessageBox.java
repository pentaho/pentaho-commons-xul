package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulMessageBox;

public class SwtMessageBox implements XulMessageBox {

  protected MessageBox messageBox;
  private String message;
  private String title;
  private Object[] defaultButtons = new Integer[]{SWT.OK};
  private Object[] buttons = defaultButtons;
  private Object icon = new Integer(SWT.ICON_INFORMATION);
  private XulElement parent;

  public SwtMessageBox(XulElement parent, String message) {
    this(parent, message, null);
  }

  public SwtMessageBox(XulElement parent, String message, String title) {
    this(parent, message, title, null);
  }

  public SwtMessageBox(XulElement parent, String message, String title, Object[] buttons) {
    this(parent, message, title, buttons, new Integer(SWT.ICON_INFORMATION));
  }

  public SwtMessageBox(XulElement parent, String message, String title, Object[] buttons, Object icon) {
    this.parent = parent;
    setMessage(message);
    setTitle(title);
    setIcon(icon);
    setButtons(buttons);
  }

  public Object[] getButtons() {
    return buttons;
  }

  private int getBitwiseStyle() {
    int style=0;
    if (icon != null){
      style |= ((Integer)icon).intValue();
    }
    for (Object button : buttons) {
     style |=((Integer)button).intValue(); 
    }
    return style;
  }
  
  /**
   * We do this so we can pick up new buttons or icons if they choose to reuse their SWTMessageBox
   */
  private void createNewMessageBox(){
    
    messageBox = new MessageBox((Shell) parent.getManagedObject(), getBitwiseStyle());
    messageBox.setMessage(getMessage());
    messageBox.setText(getTitle());
  }

  public Object getIcon() {
    return icon;
  }

  public String getMessage() {
    if (message == null)
      return "";
    return message;
  }

  public String getTitle() {
    if (title == null)
      return "";
    return title;
  }

  public void setButtons(Object[] buttons) {
    // Can't have null buttons - accept default
    this.buttons = (buttons == null) ? defaultButtons : buttons;
  }

  public void setIcon(Object icon) {
    this.icon = icon;
  }

  public void setMessage(String message) {
   this.message = message; 
  }

  public void setTitle(String title) {
   this.title = title; 
  }
  
  public int open(){
    createNewMessageBox();
    return messageBox.open();
  }

}
