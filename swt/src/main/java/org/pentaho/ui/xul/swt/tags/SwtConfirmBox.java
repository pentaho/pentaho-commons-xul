/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulConfirmBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.custom.MessageDialogBase;

public class SwtConfirmBox extends MessageDialogBase implements XulConfirmBox {

  static final String ELEMENT_NAME = "confirmbox"; //$NON-NLS-1$

  private MessageBox dialog;

  public SwtConfirmBox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( tagName );
    if ( parent instanceof SwtWindow ) {
      SwtWindow swtWindow = (SwtWindow) parent;
      setModalParent( swtWindow.getShell() );
    }
  }

  protected void createNewMessageBox() {
    Shell shell = getParentObject();

    dialog = new MessageBox( shell, acceptBtn | cancelBtn );
    dialog.setText( getTitle() );
    dialog.setMessage( this.getMessage() );
  }

  public int open() {
    createNewMessageBox();
    int retVal = dialog.open();
    notifyListeners( retVal );
    return retVal;
  }

}
