package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.XulDialogCallback;

public final class SwtPromptBox extends SwtElement implements XulPromptBox {

  private String defaultValue = null;

  private List<XulDialogCallback<String>> callbacks = new ArrayList<XulDialogCallback<String>>();
  
  static final String ELEMENT_NAME = "promptbox"; //$NON-NLS-1$
  
  private String acceptText = "OK";
  private String cancelText = "Cancel";
  private String title, message;
  private Shell parentObject;
  private XulComponent parent;
  private InputDialog dlg;

  public SwtPromptBox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("promptbox");
    this.parent = parent;
  }


  public void addDialogCallback(XulDialogCallback callback) {
    this.callbacks.add(callback);
  }

  public void removeDialogCallback(XulDialogCallback callback) {
    this.callbacks.remove(callback);
  }
  
  protected Shell getParentObject(){
    if(parentObject != null){
      return parentObject;
    } else if (getParent() instanceof SwtDialog){
      return ((SwtDialog)getParent()).getShell();
    } else if(this.parent != null){
      return (Shell) this.parent.getManagedObject();
    } else {
      return (Shell) getParent().getManagedObject();
    }
  }
  
  private void notifyListeners(Integer code){
    XulDialogCallback.Status status = XulDialogCallback.Status.CANCEL;
    
    switch (code){
      case IDialogConstants.OK_ID:
        status = XulDialogCallback.Status.ACCEPT;
        break;
      case IDialogConstants.CANCEL_ID:
        status = XulDialogCallback.Status.CANCEL;
        break;
      case IDialogConstants.CLIENT_ID + 1:
        status = XulDialogCallback.Status.ONEXTRA1;
        break;
      case IDialogConstants.CLIENT_ID + 2:
        status = XulDialogCallback.Status.ONEXTRA2;
        break;
    }
    
    for(XulDialogCallback<String> callback : callbacks){
      callback.onClose(SwtPromptBox.this, status, dlg.getValue());
    }
  }
  
  public String getValue() {
    return dlg.getValue();
  }

  public void setValue(String value) {
    defaultValue = value;
  }

  public void setCancelLabel(String label) {
    this.cancelText = label;
  }

  public Object[] getButtons() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getIcon() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getMessage() {
    return message;
  }

  public String getTitle() {
    return title;
  }

  public int open() {
    
    
    dlg = new InputDialog(getParentObject(), title, message, defaultValue, new IInputValidator(){

      public String isValid(String arg0) {
        return null;
      }
      
    });
    int retVal = dlg.open();
    notifyListeners(retVal);
    return retVal;
  }

  public void setAcceptLabel(String label) {
    this.acceptText = label;
    
  }

  public void setButtons(Object[] buttons) {
    // TODO Auto-generated method stub
    
  }

  public void setIcon(Object icon) {
    // TODO Auto-generated method stub
    
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public void setModalParent(Object parent) {
    parentObject = (Shell) parent;
  }
  

  public void setScrollable(boolean scroll) {
    // TODO Auto-generated method stub
    
  }

  public void setTitle(String title) {
    this.title = title;
  }
  
}
