package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulMessageBox;

public class SwingMessageBox implements XulMessageBox {
  
  // valid button values...
  private static final String OK = "OK";
  private static final String CANCEL = "Cancel";
  private static final String YES = "Yes";
  private static final String NO = "No";
  private static final String CLOSE = "Close";
  

  private String message;
  private String title;
  private Object[] defaultButtons = new Object[]{OK};
  private Object[] buttons = defaultButtons;
  private Object icon = new Integer(JOptionPane.INFORMATION_MESSAGE);
  private XulComponent parent;

  public SwingMessageBox(XulComponent parent, String message) {
    this(parent, message, null);
  }

  public SwingMessageBox(XulComponent parent, String message, String title) {
    this(parent, message, title, null);
  }

  public SwingMessageBox(XulComponent parent, String message, String title, Object[] buttons) {
    this(parent, message, title, buttons, JOptionPane.INFORMATION_MESSAGE);
  }

  public SwingMessageBox(XulComponent parent, String message, String title, Object[] buttons, Object icon) {
    this.parent = parent;
    setMessage(message);
    setTitle(title);
    setIcon(icon);
    setButtons(buttons);
  }

  public Object[] getButtons() {
    return buttons;
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
    int messageType = (icon instanceof Integer) ? ((Integer)icon).intValue() : JOptionPane.INFORMATION_MESSAGE;
    Icon imageIcon = (icon instanceof Icon) ? (Icon)icon : null;
    
    return JOptionPane.showOptionDialog((Component) parent.getManagedObject(), message, title, 
        JOptionPane.DEFAULT_OPTION, messageType, imageIcon, buttons, buttons[0]);
  }


}
