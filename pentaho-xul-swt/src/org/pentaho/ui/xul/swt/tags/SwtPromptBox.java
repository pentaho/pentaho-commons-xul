package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.custom.MessageDialogBase;
import org.pentaho.ui.xul.util.XulDialogCallback;

public final class SwtPromptBox extends MessageDialogBase implements XulPromptBox {

  private String defaultValue = null;

  
  static final String ELEMENT_NAME = "promptbox"; //$NON-NLS-1$
  
  private XulComponent parent;
  private InputDialog dlg;

  public SwtPromptBox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("promptbox");
    this.parent = parent;
  }



  
  public String getValue() {
    return dlg.getValue();
  }

  public void setValue(String value) {
    defaultValue = value;
  }

  public int open() {
    
    
    dlg = new InputDialog(getParentObject(), title, message, defaultValue, new IInputValidator(){

      public String isValid(String arg0) {
        return null;
      }
      
    });
    
    Image parentImg = null;
    if(getParentObject() != null){
    	parentImg = getParentObject().getImage();
    }
    if(parentImg != null){
    	dlg.setDefaultImage(parentImg);
    }
    int retVal = dlg.open();
    notifyListeners(retVal);
    return retVal;
  }




  @Override
  protected void notifyListeners(Integer code) {
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

  

  
}
