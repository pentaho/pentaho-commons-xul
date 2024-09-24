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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.graphics.Image;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.custom.MessageDialogBase;
import org.pentaho.ui.xul.util.XulDialogCallback;

public final class SwtPromptBox extends MessageDialogBase implements XulPromptBox {

  private String defaultValue = null;

  static final String ELEMENT_NAME = "promptbox"; //$NON-NLS-1$

  private XulComponent parent;
  private InputDialog dlg;

  public SwtPromptBox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "promptbox" );
    this.parent = parent;
  }

  public String getValue() {
    return dlg.getValue();
  }

  public void setValue( String value ) {
    defaultValue = value;
  }

  public int open() {

    dlg = new InputDialog( getParentObject(), title, message, defaultValue, new IInputValidator() {

      public String isValid( String arg0 ) {
        return null;
      }

    } );

    Image parentImg = null;
    if ( getParentObject() != null ) {
      parentImg = getParentObject().getImage();
    }
    if ( parentImg != null ) {
      dlg.setDefaultImage( parentImg );
    }
    int retVal = dlg.open();
    notifyListeners( retVal );
    return retVal;
  }

  @Override
  protected void notifyListeners( Integer code ) {
    XulDialogCallback.Status status = XulDialogCallback.Status.CANCEL;

    switch ( code ) {
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

    for ( XulDialogCallback<String> callback : callbacks ) {
      callback.onClose( SwtPromptBox.this, status, dlg.getValue() );
    }
  }

}
