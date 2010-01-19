package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulConfirmBox;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.DialogButton;
import org.pentaho.ui.xul.util.XulDialogCallback;

public class SwtConfirmBox extends SwtMessageBox implements XulConfirmBox {

  private List<XulDialogCallback<String>> callbacks = new ArrayList<XulDialogCallback<String>>();
  
  static final String ELEMENT_NAME = "confirmbox"; //$NON-NLS-1$
  
  private String cancelLabel;

  public SwtConfirmBox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super(self, parent, domContainer, tagName);
    this.setButtonAlignment(SWT.CENTER);
    this.setButtons(new DialogButton[]{DialogButton.ACCEPT, DialogButton.CANCEL});
  }

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
    
    Label txt = new Label(c, SWT.WRAP);
    txt.setText(getMessage());
    
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.horizontalAlignment = GridData.FILL;
    txt.setLayoutData(gd);
    
    setButtons(c);
    
    c.layout();
    c.redraw();
    shell.redraw();
  }
  
  protected void addButtonListeners(final Button btn, final int code) {
    btn.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(SelectionEvent arg0) {
        notifyListeners(code);
        dialog.close();
      }
    });
  }

  public void addDialogCallback(XulDialogCallback callback) {
    this.callbacks.add(callback);
  }

  public void removeDialogCallback(XulDialogCallback callback) {
    this.callbacks.remove(callback);
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
      callback.onClose(SwtConfirmBox.this, status, null);
    }
  }

  public void setCancelLabel(String label) {
    this.cancelLabel = label;
  }

  @Override
  protected String getButtonText(DialogButton button) {
    if(button == DialogButton.CANCEL && this.cancelLabel != null){
      return cancelLabel;
    }
    return super.getButtonText(button);
  }
  
}
