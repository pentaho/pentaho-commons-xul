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

/**
 * 
 */

package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextPane;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 * 
 */
public class SwingLabel extends SwingElement implements XulLabel {
  private JTextPane label;

  public SwingLabel( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "label" );
    label = new JTextPane();
    label.setEditable( false );
    label.setFocusable( false );
    label.setOpaque( false );
    label.setDisabledTextColor( Color.decode( "#777777" ) );

    setManagedObject( label );
    Dimension size = label.getPreferredSize();
    size.width = 100;
    label.setMinimumSize( size );
  }

  public void layout() {

  }

  public void setValue( String value ) {
    label.setText( value );

  }

  public String getValue() {
    return label.getText();

  }

  public boolean isDisabled() {
    return !label.isEnabled();
  }

  public void setDisabled( boolean dis ) {
    boolean oldValue = !label.isEnabled();
    label.setEnabled( !dis );
    this.changeSupport.firePropertyChange( "disabled", oldValue, dis );
  }

}
