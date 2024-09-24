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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.DialogButton;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.custom.BasicDialog;

public class SwtMessageBox extends SwtElement implements XulMessageBox {

  // protected BasicDialog messageBox;
  protected List<Button> buttonList = new ArrayList<Button>();
  private String message;
  private String title;
  private DialogButton[] defaultButtons = new DialogButton[] { DialogButton.ACCEPT };
  private Object[] buttons = defaultButtons;
  private Object icon = new Integer( SWT.ICON_INFORMATION );
  private XulComponent parent;
  private Shell parentObject = null;
  private boolean scrollable = false;
  private String acceptLabel = "OK";
  private int buttonAlignment = SWT.CENTER;

  public SwtMessageBox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "messagebox" );
    this.parent = parent;
    this.parent.addChild( this );
  }

  public SwtMessageBox( XulComponent parent, String message ) {
    this( parent, message, null );
  }

  public SwtMessageBox( XulComponent parent, String message, String title ) {
    this( parent, message, title, null );
  }

  public SwtMessageBox( XulComponent parent, String message, String title, Object[] buttons ) {
    this( parent, message, title, buttons, new Integer( SWT.ICON_INFORMATION ) );
  }

  public SwtMessageBox( XulComponent parent, String message, String title, Object[] buttons, Object icon ) {
    super( "messagebox" );
    this.parent = parent;
    setMessage( message );
    setTitle( title );
    setIcon( icon );
    setButtons( buttons );
  }

  public Object[] getButtons() {
    return buttons;
  }

  private int getBitwiseStyle() {
    int style = 0;
    if ( icon != null ) {
      style |= ( (Integer) icon ).intValue();
    }
    int pos = 0;
    for ( Object button : buttons ) {
      if ( button instanceof Integer ) {
        style |= ( (Integer) button ).intValue();
      } else {
        style |= pos;
      }
      pos++;
    }

    return style;
  }

  /**
   * We do this so we can pick up new buttons or icons if they choose to reuse their SWTMessageBox
   */
  protected MessageBox dialog;

  protected void createNewMessageBox() {
    Shell shell = getParentObject();
    dialog = new MessageBox( shell, getBitwiseStyle() );
    dialog.setText( getTitle() );
    dialog.setMessage( getMessage() );

  }

  public void setDefaultButtons( BasicDialog d ) {
  }

  protected String getButtonText( DialogButton button ) {
    switch ( button ) {
      case ACCEPT:
        return ( this.acceptLabel != null ) ? this.acceptLabel : button.getLabel();
      default:
        return button.getLabel();
    }
  }

  protected Composite buttonArea;

  public Object getIcon() {
    return icon;
  }

  public String getMessage() {
    if ( message == null ) {
      return "";
    }
    return message;
  }

  public String getTitle() {
    if ( title == null ) {
      return "Message:";
    }
    return title;
  }

  public void setButtons( Object[] buttons ) {
    if ( buttons == null ) {
      // Can't have null buttons - accept default
      return;
    }
    this.buttons = buttons;
  }

  public void setIcon( Object icon ) {
    this.icon = icon;
  }

  public void setMessage( String message ) {
    this.message = message;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  private int retVal = DialogButton.CANCEL.getId();

  public int open() {
    createNewMessageBox();
    retVal = dialog.open();
    return retVal;
  }

  public void setScrollable( boolean scroll ) {
    this.scrollable = scroll;
  }

  public void setModalParent( Object parent ) {
    parentObject = (Shell) parent;
  }

  protected Shell getParentObject() {
    if ( parentObject != null ) {
      return parentObject;
    } else if ( getParent() instanceof SwtDialog ) {
      return ( (SwtDialog) getParent() ).getShell();
    } else {
      return (Shell) getParent().getManagedObject();
    }
  }

  public void setAcceptLabel( String label ) {
    this.acceptLabel = label;
  }

  public void setButtonAlignment( int buttonAlignment ) {
    this.buttonAlignment = buttonAlignment;
  }

}
