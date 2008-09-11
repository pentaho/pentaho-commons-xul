package org.pentaho.ui.xul.swt.tags;

import java.awt.EventQueue;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.DialogButton;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.custom.BasicDialog;

public class SwtDialog extends SwtElement implements XulDialog {

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

  private static final Log logger = LogFactory.getLog(SwtDialog.class);

  public SwtDialog(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    
    // First, check to see if an outer context was passed before parser started...  
    if (container.getOuterContext() != null){
      possibleParent = (Shell) container.getOuterContext();
    }
    
    // If not, then try to use the API's parent parameter...
    if ((possibleParent == null) && (parent != null) && parent.getManagedObject() instanceof Shell){
      possibleParent = (Shell) parent.getManagedObject();
    }
    this.domContainer = container;
    createDialog();
  }

  private void createDialog() {
    dialog = new BasicDialog((possibleParent != null) ? possibleParent : new Shell(SWT.SHELL_TRIM));
    managedObject = dialog.getMainArea();
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

  public void setButtons(String buttonList) {
    buttons = buttonList.split(",");
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

    if (dialog == null) {
      createDialog();
    }
    
    isDialogHidden = false;

    dialog.getShell().setText(title);

    setButtons();
    
    // Because the dialog is built after the create() method is called, we 
    // need to ask the shell to try to re-determine an appropriate size for this dialog..
    if ((height > 0) && (width > 0)){
      
      // Don't allow the user to size the dialog smaller than is reasonable to 
      // layout the child components
      Point pt = dialog.getPreferredSize();
      dialog.setHeight( (pt.y < height) ? height : pt.y);
      dialog.setWidth((pt.x < width) ? width : pt.x);
    }
    dialog.resizeBounds();
    
    // Timing is everything - fire the onLoad evetns so tht anyone who is trying to
    notifyListeners(XulRoot.EVENT_ON_LOAD);
    returnCode = dialog.open();
  }
  
  public void setButtons(){
    
    if (buttons == null){
      return;
    }
    
    for (String buttonName : buttons) {
      DialogButton thisButton = DialogButton.valueOf(buttonName.trim().toUpperCase());
      Button button = dialog.createButton(thisButton, false);
      SwtButton swtButton = new SwtButton(button);
      this.addChild(swtButton);

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
    returnCode = IDialogConstants.CLOSE_ID;
    isDialogHidden = true;
    dialog.close();
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
    super.layout();

    for (XulComponent comp : this.children) {
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
    dialog.getShell().addListener(XulRoot.EVENT_ON_LOAD, new Listener() {
      public void handleEvent(Event e) {
        invoke(method);
      }
    });
  }

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

}
