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
