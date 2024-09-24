/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

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
