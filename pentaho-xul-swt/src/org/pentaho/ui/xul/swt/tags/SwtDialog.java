package org.pentaho.ui.xul.swt.tags;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.DialogButton;
import org.pentaho.ui.xul.swt.custom.BasicDialog;
import org.pentaho.ui.xul.util.Orient;

public class SwtDialog extends AbstractSwtXulContainer implements XulDialog {

  Shell possibleParent;

  XulDomContainer domContainer = null;

  private BasicDialog dialog = null;
  
  private String title = null;

  private String onload;
  private String onclose;
  private String onunload;

  private XulDialogheader header;

  private int height = -999;

  private int width = -999;

  private boolean isDialogHidden = true;
  
  private int returnCode = -9999;
  
  private BUTTON_ALIGN buttonAlignment;

  private enum BUTTON_ALIGN {
    START, CENTER, END, LEFT, RIGHT, MIDDLE
  };

  private String buttonlabelaccept;

  private String buttonlabelcancel;

  private String buttonlabelextra1;

  private String buttonlabelextra2;

  private String[] buttons = new String[]{"accept", "cancel"};

  private String ondialogaccept;

  private String ondialogcancel;

  private String ondialogextra1;

  private String ondialogextra2;
  
  private boolean resizable = false;
  
  private boolean buttonsCreated = false;
  
  private String appIcon;

  private static final Log logger = LogFactory.getLog(SwtDialog.class);

  public SwtDialog(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    
    // First, check to see if an outer context was passed before parser started...  
    if (container.getOuterContext() != null){
      possibleParent = (Shell) container.getOuterContext();
    }
    
    orient = Orient.VERTICAL;
    
    // If not, then try to use the API's parent parameter...
    if ((possibleParent == null) && (parent != null) && parent.getManagedObject() instanceof Shell){
      possibleParent = (Shell) parent.getManagedObject();
    }
    this.domContainer = container;
    this.setId(self.getAttributeValue("ID"));
    
    // TODO: deferr this creation until later when all attributes are assigned. For now just get the 
    // resizable one
    String resizableStr = self.getAttributeValue("resizable");
    this.setResizable(resizableStr != null && resizableStr.equals("true"));
    createDialog();
    
    if(self != null) {
      // Extract appIcon
      setAppicon(self.getAttributeValue("appicon"));
    }
  }

  private void createDialog() {
    
    BasicDialog newDialog = new BasicDialog((possibleParent != null) ? possibleParent : new Shell(SWT.SHELL_TRIM), true);
    dialog = newDialog;
    dialog.getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
    dialog.getShell().addListener(SWT.Close, new Listener() {
      public void handleEvent(Event event) {
        event.doit = false;
        if(dialog.getShell() != null && dialog.getShell().isDisposed() == false){
          hide();
        }
      }
    });
    
    Composite c = new Composite((Composite) dialog.getMainDialogArea(), SWT.NONE);
    
    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.grabExcessVerticalSpace = true;
    gd.grabExcessHorizontalSpace = true;
    
    c.setLayoutData(gd);

    setManagedObject(c);
  }
  
  public Shell getShell(){
    return dialog != null ? dialog.getShell() : null;
  }

  public String getButtonlabelaccept() {
    return buttonlabelaccept;
  }

  public String getButtonlabelcancel() {
    return buttonlabelcancel;
  }

  public String getButtons() {
    return StringUtils.join(buttons, ",");
  }

  public String getOndialogaccept() {
    return ondialogaccept;
  }

  public String getOndialogcancel() {
    return ondialogcancel;
  }

  public String getTitle() {
    return title;
  }

  public void setButtonlabelaccept(String label) {
    this.buttonlabelaccept = label;
  }

  public void setButtonlabelcancel(String label) {
    this.buttonlabelcancel = label;
  }
  
  public void setButtons(){
    setButtons(dialog);
    buttonsCreated = true;
  }

  public void setButtons(String buttonList) {
    if(buttonList.equals("")){
      buttons = null;
    } else {
      buttons = buttonList.split(",");
    }
    if(buttonsCreated){
      setButtons(dialog);
    }
  }

  public void setOndialogaccept(String command) {
    this.ondialogaccept = command;

  }

  public void setOndialogcancel(String command) {
    this.ondialogcancel = command;
    if (ondialogcancel != null){
     
      dialog.addShellListener(new ShellAdapter(){
        public void shellClosed(ShellEvent e){
            invoke(ondialogcancel);
        }
      });
            
    }
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void show() {
    show(true);
  }
  
  public void show(boolean force) {

    //createDialog();
    
    if((force) || (!buttonsCreated)){
      setButtons();
    }
    
    isDialogHidden = false;

    dialog.getShell().setText(title);

    
    // Because the dialog is built after the create() method is called, we 
    // need to ask the shell to try to re-determine an appropriate size for this dialog..
    if ((height > 0) && (width > 0)){
      
      // Don't allow the user to size the dialog smaller than is reasonable to 
      // layout the child components
      // REMOVED: although the idea is sound, labels of great length that are ment to wrap
      //      will report their preferred size and quite large, which when applied is undesirable.
      //Point pt = dialog.getPreferredSize();
      //dialog.setHeight( (pt.y < height) ? height : pt.y);
      //dialog.setWidth((pt.x < width) ? width : pt.x);
      
      
      // Don't allow the user to size the dialog smaller than is reasonable to 
      // layout the child components
      dialog.setHeight(height);
      dialog.setWidth(width);
      
    }
    dialog.resizeBounds();
    
    // Timing is everything - fire the onLoad evetns so tht anyone who is trying to
    notifyListeners(XulRoot.EVENT_ON_LOAD);
    returnCode = dialog.open();
    
  //  dialog.setBlockOnOpen(true);
//    dialog.getShell().setVisible(true);
  }
  
  public void setButtons(final BasicDialog d){
    
    if (buttons == null){
      d.getButtonArea().setVisible(false);
      d.getButtonArea().getParent().setVisible(false);
      ((GridData) d.getButtonArea().getParent().getLayoutData()).exclude = true;
      d.getShell().layout(true);
      return;
    }
    
    for (String buttonName : buttons) {
      if(StringUtils.isEmpty(buttonName)){
        return;
      }
      DialogButton thisButton = DialogButton.valueOf(buttonName.trim().toUpperCase());
      
      SwtButton swtButton = null;
      SwtButton existingButton = (this.getDocument() != null) ? (SwtButton) this.getElementById(this.getId()+"_" + buttonName.trim().toLowerCase()) : null;
      if(this.getId() != null && existingButton != null){
        //existing button, just needs a new Widget parent
        swtButton = existingButton;
        Widget w = (Widget)existingButton.getManagedObject(); 
        if ((w==null)|| (w.isDisposed())){
          Button button = d.createButton(thisButton, false);
          swtButton.setButton(button);
        }
      } else {
        //new button needed
        Button button = d.createButton(thisButton, false);
        swtButton = new SwtButton(button);
        swtButton.setId(this.getId()+"_" + buttonName.trim().toLowerCase());
        this.addChild(swtButton);
          
      }
      switch (thisButton){
        case ACCEPT:
          if ((getButtonlabelaccept() != null) && (getButtonlabelaccept().trim().length() > 0)){
            swtButton.setLabel(getButtonlabelaccept());
          }
          if ((getOndialogaccept() != null) && (getOndialogaccept().trim().length() > 0)){
            swtButton.setOnclick(getOndialogaccept());
          }
          break;
        case CANCEL:
          if ((getButtonlabelcancel() != null) && (getButtonlabelcancel().trim().length() > 0)){
            swtButton.setLabel(getButtonlabelcancel());
          }
          if ((getOndialogcancel() != null) && (getOndialogcancel().trim().length() > 0)){
            swtButton.setOnclick(getOndialogcancel());
          }
          break;
        case EXTRA1:
          if ((getButtonlabelextra1() != null) && (getButtonlabelextra1().trim().length() > 0)){
            swtButton.setLabel(getButtonlabelextra1());
          }
          if ((getOndialogextra1() != null) && (getOndialogextra1().trim().length() > 0)){
            swtButton.setOnclick(getOndialogextra1());
          }
          break;
        case EXTRA2:
          if ((getButtonlabelextra2() != null) && (getButtonlabelextra2().trim().length() > 0)){
            swtButton.setLabel(getButtonlabelextra2());
          }
          if ((getOndialogextra2() != null) && (getOndialogextra2().trim().length() > 0)){
            swtButton.setOnclick(getOndialogextra2());
          }
          break;
      }
      
    }
    
  }

  public void hide() {
    if(dialog.getMainArea().isDisposed()){
      return;
    }
    returnCode = IDialogConstants.CLOSE_ID;
    
    BasicDialog newDialog = new BasicDialog((possibleParent != null) ? possibleParent : new Shell(SWT.SHELL_TRIM), getResizable());
    Control[] controlz = newDialog.getMainArea().getChildren();
    for(Control c : controlz){
      c.dispose();
    }
    
    Control[] controls = dialog.getMainArea().getChildren();
    for(Control c : controls){
      c.setParent(newDialog.getMainArea());
    }
    setButtons(newDialog);
    setAppicon(this.appIcon);
    
    newDialog.getShell().layout();
    
    dialog.close();
    isDialogHidden = true;

    dialog = newDialog;
    dialog.getShell().addListener(SWT.Close, new Listener() {
      public void handleEvent(Event event) {
        hide();
        event.doit = false;
      }
    });
    
    
    setManagedObject(dialog.getMainArea());
    
  }

  public void setVisible(boolean visible) {
    if (visible) {
      show();
    } else {
      hide();
    }
  }

  @Override
  public void layout() {
    setButtons();
    super.layout();

    for (XulComponent comp : getChildNodes()) {
      if (comp instanceof XulDialogheader) {
        header = (XulDialogheader) comp;
      }
    }
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public String getButtonalign() {
    return this.buttonAlignment.toString().toLowerCase();
  }

  public void setButtonalign(String align) {
    this.buttonAlignment = SwtDialog.BUTTON_ALIGN.valueOf(align.toUpperCase());

  }

  public String getOnload() {
    return onload;
  }

  public void setOnload(final String method) {
    this.onload = method;
    
    
    // @TODO This whole listener pattern needs to be replaced with a generic solution 
     
    dialog.getShell().addListener(XulRoot.EVENT_ON_LOAD, new Listener() {
      public void handleEvent(Event e) {
        if(!StringUtils.isEmpty(method)){
          
          // only call this if the application is ready. Otherwise it's being handled in the main of the 
          // program
          if(SwtDialog.this.domContainer.isInitialized()){
            invoke(method);
          }
        }
      }
    });
  }

  /**
   * @deprecated This will be replaced by an agnostic listener pattern in the next version of Xul
   * @param event
   */
  @Deprecated
  public void notifyListeners(int event) {
    if (!dialog.getShell().isDisposed()) {
      dialog.getShell().notifyListeners(event, new Event());
    }
  }

  public boolean isHidden() {
    return isDialogHidden;
  }

  public String getButtonlabelextra1() {
    return buttonlabelextra1;
  }

  public void setButtonlabelextra1(String buttonlabelextra1) {
    this.buttonlabelextra1 = buttonlabelextra1;
  }

  public String getButtonlabelextra2() {
    return buttonlabelextra2;
  }

  public void setButtonlabelextra2(String buttonlabelextra2) {
    this.buttonlabelextra2 = buttonlabelextra2;
  }

  public String getOndialogextra1() {
    return ondialogextra1;
  }

  public void setOndialogextra1(String ondialogextra1) {
    this.ondialogextra1 = ondialogextra1;
  }

  public String getOndialogextra2() {
    return ondialogextra2;
  }

  public void setOndialogextra2(String ondialogextra2) {
    this.ondialogextra2 = ondialogextra2;
  }

  public XulDomContainer getXulDomContainer() {
    return this.domContainer;
  }

  public void setXulDomContainer(XulDomContainer xulDomContainer) {
   this.domContainer = xulDomContainer;
  }
  
  public int getReturnCode(){
    return returnCode;
  }

  public Object getRootObject() {
    return dialog.getShell();
  }
  
  public String getOnclose() {
    return onclose;
  }

  public String getOnunload() {
    return onunload;
  }

  public void setOnclose(String onclose) {
    this.onclose = onclose;
  }

  public void setOnunload(String onunload) {
    this.onunload = onunload;
  }

  public void invokeLater(Runnable runnable) {
    dialog.getShell().getDisplay().asyncExec(runnable);
  }

  public Boolean getResizable() {
    return resizable;
  }

  public void setResizable(Boolean resizable) {
    this.resizable = resizable;
  }

  public void setModal(Boolean modal) {
   throw new RuntimeException("Not Yet Implemented");
  }
  
  public void setAppicon(String icon) {
    this.appIcon = icon;
    
    if(appIcon == null) {
      return;
    }
    
    Display d = dialog.getShell().getDisplay();
    if(d == null){
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }
    
    InputStream in = null;
    try{
      in = SwtButton.class.getClassLoader().getResourceAsStream(this.domContainer.getXulLoader().getRootDir()+appIcon);
      if(in == null){
        File f = new File(icon);
        if(f.exists()){
          try {
            in = new FileInputStream(f);
          } catch (FileNotFoundException e) {}
        } else {
          logger.warn("could not find image: "+appIcon);
          return;
        }
      }
      Image img = new Image(dialog.getShell().getDisplay(), in);
      if(img != null) {
        dialog.getShell().setImage(img);
      }
    } finally {
      try{
        if(in != null){
          in.close();
        }
      } catch(IOException ignored){}
    }
    
  }
  
}
