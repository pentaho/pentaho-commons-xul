package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtMessageBox extends SwtElement implements XulMessageBox {

  protected MessageBox messageBox;
  private String message;
  private String title;
  private Object[] defaultButtons = new Integer[]{SWT.OK};
  private Object[] buttons = defaultButtons;
  private Object icon = new Integer(SWT.ICON_INFORMATION);
  private XulComponent parent;

  public SwtMessageBox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("messagebox");
    this.parent = parent;
    this.parent.addChild(this);
  }
  
  public SwtMessageBox(XulComponent parent, String message) {
    this(parent, message, null);
  }

  public SwtMessageBox(XulComponent parent, String message, String title) {
    this(parent, message, title, null);
  }

  public SwtMessageBox(XulComponent parent, String message, String title, Object[] buttons) {
    this(parent, message, title, buttons, new Integer(SWT.ICON_INFORMATION));
  }

  public SwtMessageBox(XulComponent parent, String message, String title, Object[] buttons, Object icon) {
    super("messagebox");
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
    Shell shell = null;
    if (getParent() instanceof SwtDialog){
      shell = ((SwtDialog)getParent()).getShell();
    }else{
      shell = (Shell) getParent().getManagedObject();
    }
    messageBox = new MessageBox(shell, getBitwiseStyle());
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

  public void setScrollable(boolean scroll) {
    
        // TODO Auto-generated method stub 
      
  }

}
