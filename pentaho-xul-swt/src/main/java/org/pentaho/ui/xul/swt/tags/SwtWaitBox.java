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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.WaitBoxRunnable;
import org.pentaho.ui.xul.components.XulWaitBox;
import org.pentaho.ui.xul.dom.Element;

public class SwtWaitBox extends SwtProgressmeter implements XulWaitBox {

  private Shell parentObject = null;
  private Shell dialog;
  private WaitBoxRunnable runnable;
  private Composite composite;
  private Thread thread;
  private boolean canCancel;
  private String message = "Please Wait...";
  private String title = "Please Wait";
  private String cancelLabel = "Cancel";

  public SwtWaitBox( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( self, parent, container, tagName );
    parent.addChild( this );

  }

  @Override
  public void layout() {
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

  public void setModalParent( Object parent ) {
    parentObject = (Shell) parent;
  }

  protected Shell createDialog() {
    dialog = new Shell( getParentObject(), SWT.DIALOG_TRIM | SWT.RESIZE );
    if ( getParentObject().getImage() != null ) {
      dialog.setImage( getParentObject().getImage() );
    }
    dialog.getShell().setSize( 300, 150 );
    GridLayout gl = new GridLayout();
    gl.verticalSpacing = 4;
    gl.marginLeft = 8;
    gl.marginRight = 8;

    composite = dialog;
    composite.setLayout( gl );
    dialog.addShellListener( new ShellAdapter() {

      @Override
      public void shellClosed( ShellEvent arg0 ) {
        stop();
      }

    } );
    dialog.setText( this.getTitle() );

    Label lbl = new Label( composite, SWT.WRAP | SWT.CENTER );
    lbl.setText( this.getMessage() );
    GridData gd = new GridData( SWT.CENTER, SWT.CENTER, true, true, 1, 1 );

    lbl.setLayoutData( gd );

    progressmeter = createNewProgressmeter( composite );

    gd = new GridData( SWT.CENTER, SWT.CENTER, true, true, 1, 1 );
    gd.grabExcessHorizontalSpace = true;
    gd.horizontalAlignment = gd.FILL;

    progressmeter.setLayoutData( gd );
    // progressmeter.setSize(150, progressmeter.getSize().y);

    if ( canCancel ) {
      Button button = new Button( composite, SWT.PUSH );
      button.setText( cancelLabel );
      button.addSelectionListener( new SelectionAdapter() {

        @Override
        public void widgetSelected( SelectionEvent arg0 ) {
          SwtWaitBox.this.stop();
        }

      } );
      button.setLayoutData( new GridData( SWT.CENTER, SWT.CENTER, true, true, 1, 1 ) );
    } else {
      lbl = new Label( composite, SWT.None );

      lbl.setLayoutData( new GridData( SWT.CENTER, SWT.CENTER, true, true, 1, 1 ) );
    }

    return dialog;
  }

  public void start() {
    dialog = createDialog();
    dialog.layout( true, true );
    thread = new Thread( runnable );
    thread.start();
    dialog.open();
  }

  public void stop() {
    runnable.cancel();
    thread.interrupt();
    Display.getDefault().asyncExec( new Runnable() {
      public void run() {
        if ( dialog.isDisposed() == false ) {
          dialog.close();
        }
      }
    } );
  }

  public void setRunnable( WaitBoxRunnable runnable ) {
    this.runnable = runnable;
  }

  public void setCanCancel( boolean canCancel ) {
    this.canCancel = canCancel;
  }

  public String getMessage() {
    return message;
  }

  public String getTitle() {
    return title;
  }

  public void setMessage( String message ) {
    this.message = message;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public void setDialogParent( Object parent ) {
    parentObject = (Shell) parent;
  }

  public void setCancelLabel( String lbl ) {
    this.cancelLabel = lbl;
  }

}
