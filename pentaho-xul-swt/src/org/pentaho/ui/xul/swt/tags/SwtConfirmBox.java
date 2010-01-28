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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulConfirmBox;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.DialogButton;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.custom.MessageDialogBase;
import org.pentaho.ui.xul.util.XulDialogCallback;

public class SwtConfirmBox extends MessageDialogBase implements XulConfirmBox {

  
  static final String ELEMENT_NAME = "confirmbox"; //$NON-NLS-1$

  private MessageBox dialog;
  
  public SwtConfirmBox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super(tagName);
    
  }

  protected void createNewMessageBox(){
    Shell shell = getParentObject();
    
    dialog = new MessageBox(shell, acceptBtn | cancelBtn);
    dialog.setText(getTitle());
    dialog.setMessage(this.getMessage());
  }
  


  public int open() {
    createNewMessageBox();
    int retVal = dialog.open();
    notifyListeners(retVal);
    return retVal;
  }

  
}
