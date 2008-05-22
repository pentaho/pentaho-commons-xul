package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMessageBox extends SwingElement implements XulMessageBox {
  
  // valid button values...
  private static final String OK = "OK";
  private static final String CANCEL = "Cancel";
  private static final String YES = "Yes";
  private static final String NO = "No";
  private static final String CLOSE = "Close";
  

  private String message = "Default Message";
  private String title = "Message Box";
  private Object[] defaultButtons = new Object[]{OK};
  private Object[] buttons = defaultButtons;
  private Object icon = new Integer(JOptionPane.INFORMATION_MESSAGE);

  public SwingMessageBox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("messagebox");
    parent = domContainer.getDocumentRoot().getRootElement();
    parent.addChild(this);
  }
  
  public Object[] getButtons() {
    return buttons;
  }


  public Object getIcon() {
    return icon;
  }

  public String getMessage() {
    return message;
  }

  public String getTitle() {
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
    
    return JOptionPane.showOptionDialog((Component) getParent().getManagedObject(), message, title, 
        JOptionPane.DEFAULT_OPTION, messageType, imageIcon, buttons, buttons[0]);
  }


}
