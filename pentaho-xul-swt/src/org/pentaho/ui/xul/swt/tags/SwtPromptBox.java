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
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.DialogButton;
import org.pentaho.ui.xul.util.XulDialogCallback;

public final class SwtPromptBox extends SwtMessageBox implements XulPromptBox {

  private Text textbox;
  private String defaultValue = null;

  private List<XulDialogCallback<String>> callbacks = new ArrayList<XulDialogCallback<String>>();
  
  static final String ELEMENT_NAME = "promptbox"; //$NON-NLS-1$

  public SwtPromptBox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super(self, parent, domContainer, tagName);
    this.setButtonAlignment(SWT.RIGHT);
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
    gd.horizontalAlignment = GridData.FILL;
    txt.setLayoutData(gd);

    textbox = new Text(c, SWT.BORDER);
    textbox.setText(defaultValue!=null?defaultValue:"");
    gd = new GridData();
    gd.grabExcessVerticalSpace = true;
    gd.horizontalAlignment = GridData.FILL;
    textbox.setLayoutData(gd);
    
    setButtons(c);
    
    for (final Button button : buttonList) {
      button.addSelectionListener(new SelectionAdapter(){
        public void widgetSelected(SelectionEvent arg0) {
          notifyListeners((Integer)button.getData());
          dialog.close();
        }
      });    
    }

    c.layout();
    c.redraw();
    shell.redraw();
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
      callback.onClose(SwtPromptBox.this, status, textbox.getText());
    }
  }
  
  public String getValue() {
    return this.textbox.getText();
  }

  public void setValue(String value) {
    defaultValue = value;
  }

  public void setCancelLabel(String label) {
    // TODO Auto-generated method stub
  }

}
