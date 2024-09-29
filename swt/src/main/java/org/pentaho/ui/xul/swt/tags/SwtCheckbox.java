/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.dom.Element;

public class SwtCheckbox extends SwtButton implements XulCheckbox {

  private static final Log logger = LogFactory.getLog( SwtCheckbox.class );
  public static final String CHECKED = "checked";

  private String command;

  public SwtCheckbox( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( null, parent, container, tagName );
  }

  @Override
  protected Button createNewButton( Composite parent ) {
    final Button button = new Button( parent, SWT.CHECK );
    button.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        changeSupport.firePropertyChange( CHECKED, !button.getSelection(), button.getSelection() );
      }
    } );
    return button;
  }

  public boolean isChecked() {
    return button.getSelection();
  }

  public void setChecked( boolean checked ) {
    boolean prev = isChecked();
    if ( ( !button.isDisposed() ) && ( button != null ) ) {
      button.setSelection( checked );
    }
    changeSupport.firePropertyChange( CHECKED, null, checked );
  }

  public void setCommand( final String method ) {
    command = method;
    button.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent arg0 ) {
        invoke( method );
      }
    } );
  }

  public String getCommand() {
    return command;
  }

}
