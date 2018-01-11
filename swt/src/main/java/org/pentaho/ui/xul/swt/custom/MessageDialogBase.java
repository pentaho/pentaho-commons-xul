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

package org.pentaho.ui.xul.swt.custom;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.XulDialogCallback;

public class MessageDialogBase extends SwtElement {

  protected List<XulDialogCallback<String>> callbacks = new ArrayList<XulDialogCallback<String>>();

  private Shell parentObject;

  protected int acceptBtn = DialogConstant.YES.getValue();
  protected int cancelBtn = DialogConstant.NO.getValue();
  protected String title, message;
  private Log logger = LogFactory.getLog( MessageDialogBase.class );

  protected int icon = 0;

  public MessageDialogBase( String tagName ) {
    super( tagName );
  }

  public void addDialogCallback( XulDialogCallback callback ) {
    this.callbacks.add( callback );
  }

  public void removeDialogCallback( XulDialogCallback callback ) {
    this.callbacks.remove( callback );
  }

  protected Shell getParentObject() {
    if ( parentObject != null ) {
      return parentObject;
    } else if ( getParent() instanceof XulRoot ) {
      return (Shell) ( (XulRoot) getParent() ).getRootObject();
    } else if ( getParent() != null ) {
      return (Shell) getParent().getManagedObject();
    } else {
      return Display.getCurrent().getActiveShell();
    }
  }

  public Object[] getButtons() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setButtons( Object[] buttons ) {
    for ( Object o : buttons ) {
      if ( o instanceof String ) {
        o =
            ( "ACCEPT".equalsIgnoreCase( (String) o ) ) ? DialogConstant.OK
                : ( "CANCEL".equalsIgnoreCase( (String) o ) ) ? DialogConstant.CANCEL : o;
      }
      DialogConstant con = (DialogConstant) o;
      switch ( con ) {
        case YES:
        case OK:
          acceptBtn = con.getValue();
          break;
        case NO:
        case CANCEL:
          cancelBtn = con.getValue();
      }
    }
  }

  public Object getIcon() {
    // TODO Auto-generated method stub
    return null;
  }

  protected void notifyListeners( Integer code ) {
    XulDialogCallback.Status status = XulDialogCallback.Status.CANCEL;

    switch ( code ) {
      case SWT.YES:
      case SWT.OK:
        status = XulDialogCallback.Status.ACCEPT;
        break;
      case SWT.NO:
      case SWT.CANCEL:
        status = XulDialogCallback.Status.CANCEL;
        break;
    }

    for ( XulDialogCallback<String> callback : callbacks ) {
      callback.onClose( this, status, null );
    }
  }

  public void setModalParent( Object parent ) {
    parentObject = (Shell) parent;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage( String message ) {
    this.message = message;
  }

  public void setCancelLabel( String str ) {
    logger.debug( "Cannot set button label text in SWT. Use setButtons() instead" );
  }

  public void setAcceptLabel( String str ) {
    logger.debug( "Cannot set button label text in SWT. Use setButtons() instead" );
  }

  public void setIcon( Object icon ) {
    if ( icon instanceof DialogConstant == false ) {
      logger.error( "Icon needs to be an DialogConstant value" );
      return;
    }
    icon = ( (DialogConstant) icon ).getValue();

  }

  public void setScrollable( boolean scroll ) {
    logger.error( "SWT does not support scrolling of message area." );

  }
}
