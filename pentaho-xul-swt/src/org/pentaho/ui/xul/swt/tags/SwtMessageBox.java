package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.DialogButton;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.custom.BasicDialog;

public class SwtMessageBox extends SwtElement implements XulMessageBox {

  protected BasicDialog messageBox;
  private String message;
  private String title;
  private DialogButton[] defaultButtons = new DialogButton[]{DialogButton.ACCEPT};
  private Object[] buttons = defaultButtons;
  private Object icon = new Integer(SWT.ICON_INFORMATION);
  private XulComponent parent;
  private Shell parentObject = null;
  private boolean scrollable = false;

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
    int pos = 0;
    for (Object button : buttons) {
      if(button instanceof Integer){
        style |=((Integer)button).intValue();
      } else {
        style |= pos;
      }
      pos++;
    }
    return style;
  }
  
  /**
   * We do this so we can pick up new buttons or icons if they choose to reuse their SWTMessageBox
   */
  private Shell dialog;
  private void createNewMessageBox(){
    Shell shell = getParentObject();
    
//    messageBox = new MessageBox(shell, getBitwiseStyle());
//    messageBox.setMessage(getMessage());
//    messageBox.setText(getTitle());
    
    
    dialog = new Shell(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.RESIZE);
    dialog.setText(getTitle());
    dialog.setLayout(new GridLayout());
    if(getWidth() > 0 && getHeight() > 0){
      dialog.setSize(getWidth(), getHeight());
    } else {
      dialog.setSize(300, 175);
    }
    
//    messageBox = new BasicDialog(shell);
//    messageBox.setTitle(getTitle());
//    messageBox.setWidth(350);
//    messageBox.setHeight(200);
    Composite c = new Composite(dialog, SWT.None);//(Composite) messageBox.getMainDialogArea();
    c.setLayout(new GridLayout());
    
    GridData gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalAlignment = GridData.FILL;
    c.setLayoutData(gd);
    
    if(!scrollable){
      Label txt = new Label(c, SWT.WRAP | SWT.CENTER);
      txt.setText(getMessage());
      
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.grabExcessVerticalSpace = true;
      gd.verticalAlignment = GridData.CENTER;
      gd.horizontalAlignment = GridData.FILL;
      txt.setLayoutData(gd);
    } else {
      int style = SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL ;
      Text txt = new Text(c, style);
      txt.setText(getMessage());
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.grabExcessVerticalSpace = true;
      gd.verticalAlignment = GridData.FILL;
      gd.horizontalAlignment = GridData.FILL;
      txt.setLayoutData(gd);
    }
    
    setButtons(c);
    c.layout();
    c.redraw();
    shell.redraw();
    
  }

  public void setDefaultButtons(BasicDialog d){
    
  }

  private Composite buttonArea;
  public void setButtons(Composite c){
    buttonArea = new Composite(c, SWT.None);
    
    GridData gd = new GridData();
    gd.grabExcessHorizontalSpace =  true;
    gd.horizontalAlignment = GridData.CENTER;
    gd.verticalAlignment = GridData.CENTER;
    buttonArea.setLayoutData(gd);
    buttonArea.setLayout(new RowLayout());
    
    if (buttons == null){
      return;
    }
    if(buttons instanceof String[]){
      for (String buttonName :  (String[]) buttons) {
        DialogButton thisButton = DialogButton.valueOf(buttonName.trim().toUpperCase());
      
        Button btn = new Button(buttonArea, SWT.PUSH);
        btn.setText(thisButton.getLabel());
        btn.setData(thisButton.getId());
      }
    } else {
      for (DialogButton thisButton:  (DialogButton[]) buttons) {
        Button btn = new Button(buttonArea, SWT.PUSH);
        btn.setText(thisButton.getLabel());
        btn.setData(thisButton.getId());
        final int code = thisButton.getId();
        btn.addSelectionListener(new SelectionAdapter(){
          public void widgetSelected(SelectionEvent arg0) {
            retVal = code;
            dialog.close();
          }
        });
        if(buttons.length == 1){
          dialog.setDefaultButton(btn);
        }
      }
    }

    
    buttonArea.layout();
    buttonArea.redraw();
    dialog.layout();
    dialog.redraw();
  }
  
  public Object getIcon() {
    return icon;
  }

  public String getMessage() {
    if (message == null){
      return "";
    }
    return message;
  }

  public String getTitle() {
    if (title == null){
      return "Message:";
    }
    return title;
  }

  public void setButtons(Object[] buttons) {
    if(buttons == null){
      // Can't have null buttons - accept default
      return;
    }
    this.buttons = buttons;
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
  
  private int retVal = DialogButton.CANCEL.getId();
  
  public int open(){
    createNewMessageBox();
    dialog.open();
    while (!dialog.isDisposed()) {
      if (!dialog.getDisplay().readAndDispatch()) {
        dialog.getDisplay().sleep();
      }
    }
    return retVal;
  }

  public void setScrollable(boolean scroll) {
    this.scrollable = scroll;
  }

  public void setModalParent(Object parent) {
    parentObject = (Shell) parent;
  }
  
  private Shell getParentObject(){
    if(parentObject != null){
      return parentObject;
    } else if (getParent() instanceof SwtDialog){
      return ((SwtDialog)getParent()).getShell();
    }else{
      return (Shell) getParent().getManagedObject();
    }
  }

}
