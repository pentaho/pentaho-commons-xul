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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulRadio;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtRadio extends SwtElement implements XulRadio {

  private String value;
  private Button radioButton;
  private static final Log logger = LogFactory.getLog( SwtRadio.class );

  public SwtRadio( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "radio" );
    radioButton = new Button( (Composite) parent.getManagedObject(), SWT.RADIO );
    setManagedObject( radioButton );

    radioButton.addSelectionListener( new SelectionListener() {

      public void widgetDefaultSelected( SelectionEvent e ) {
      }

      public void widgetSelected( SelectionEvent arg0 ) {
        logger.debug( "firing selected property change: isSelected=" + radioButton.getSelection() );
        SwtRadio.this.changeSupport.firePropertyChange( "selected", null, radioButton.getSelection() );

      }
    } );

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulRadio#isSelected()
   */
  public boolean isSelected() {
    // TODO Auto-generated method stub
    return radioButton.getSelection();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulRadio#setSelected(boolean)
   */
  public void setSelected( boolean selected ) {
    radioButton.setSelection( selected );
  }

  public void layout() {
  }

  public void setLabel( String label ) {
    radioButton.setText( label );
  }

  public String getLabel() {
    return radioButton.getText();
  }

  public boolean isDisabled() {
    return !radioButton.isEnabled();
  }

  public void setDisabled( boolean dis ) {
    radioButton.setEnabled( !dis );
  }

  public void setCommand( final String method ) {
    radioButton.addSelectionListener( new SelectionListener() {

      public void widgetDefaultSelected( SelectionEvent e ) {
      }

      public void widgetSelected( SelectionEvent arg0 ) {
        invoke( method );
      }
    } );
  }

  public String getValue() {
    return value;
  }

  public void setValue( String aValue ) {
    String previousVal = this.value;
    this.value = aValue;
    this.changeSupport.firePropertyChange( "value", previousVal, aValue );
  }
}
