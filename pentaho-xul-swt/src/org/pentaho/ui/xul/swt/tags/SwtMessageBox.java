package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
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

  //protected BasicDialog messageBox;
  protected List<Button> buttonList = new ArrayList<Button>();
  private String message;
  private String title;
  private DialogButton[] defaultButtons = new DialogButton[]{DialogButton.ACCEPT};
  private Object[] buttons = defaultButtons;
  private Object icon = new Integer(SWT.ICON_INFORMATION);
  private XulComponent parent;
  private Shell parentObject = null;
  private boolean scrollable = false;
  private String acceptLabel = "OK";
  private int buttonAlignment = SWT.CENTER;
  
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
  protected Shell dialog;
  
  protected void createNewMessageBox(){
    Shell shell = getParentObject();
    
    dialog = new Shell(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.RESIZE);
    dialog.setText(getTitle());
    dialog.setLayout(new GridLayout());
    if(getWidth() > 0 && getHeight() > 0){
      dialog.setSize(getWidth(), getHeight());
    } else {
      dialog.setSize(300, 175);
    }
    
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

  protected Composite buttonArea;
  
  public void setButtons(Composite c){
    buttonArea = new Composite(c, SWT.None);
    
    GridData gd = new GridData();
    gd.grabExcessHorizontalSpace =  true;
    if (buttonAlignment == SWT.CENTER){
      gd.horizontalAlignment = GridData.CENTER;
      gd.verticalAlignment = GridData.CENTER;
    }else{ //right align
      gd.horizontalAlignment = GridData.END;
      gd.verticalAlignment = GridData.END;
    }
    buttonArea.setLayoutData(gd);
    buttonArea.setLayout(new RowLayout());
    
    
    if (buttons == null){
      return;
    }
    
    DialogButton[] buttonsToUse = new DialogButton[buttons.length];
    
    // If we only have strings, create DialogButtons, then process EXACTLY the same way.  
    if(buttons instanceof String[]){
      for (int i = 0; i < buttons.length; i++) {
        String buttonName = ((String)buttons[i]).trim().toUpperCase();
        DialogButton thisButton = DialogButton.valueOf(buttonName);
        buttonsToUse[i] = thisButton;
      }
    } else{
      buttonsToUse = (DialogButton[])buttons;
    }

    for (DialogButton thisButton:  buttonsToUse) {
      Button btn = new Button(buttonArea, SWT.PUSH);
      btn.setText(thisButton.getLabel());
      btn.setData(thisButton.getId());
      buttonList.add(btn);
      final int code = thisButton.getId();
      addButtonListeners(btn, code);
      
      //TODO Should this eventually be an option on the messasgebox that the 
      // programmer can set? 
      if(thisButton.equals(DialogButton.ACCEPT)){
        dialog.setDefaultButton(btn);
      }
      
    }

    buttonArea.layout();
    buttonArea.redraw();
    dialog.layout();
    dialog.redraw();
  }
  
  protected void addButtonListeners(final Button btn, final int code) {
    btn.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(SelectionEvent arg0) {
        retVal = code;
        dialog.close();
      }
    });
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
  
  protected Shell getParentObject(){
    if(parentObject != null){
      return parentObject;
    } else if (getParent() instanceof SwtDialog){
      return ((SwtDialog)getParent()).getShell();
    }else{
      return (Shell) getParent().getManagedObject();
    }
  }

  public void setAcceptLabel(String label) {
    this.acceptLabel = label;  
  }

  public void setButtonAlignment(int buttonAlignment) {
    this.buttonAlignment = buttonAlignment;
  }
  
}
