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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 * 
 */
public class SwingCheckbox extends SwingElement implements XulCheckbox {

  private JCheckBox checkBox;
  private static final Log logger = LogFactory.getLog( SwingCheckbox.class );
  private String oncommand;

  // setSelected firing off event durring BeanUtils copying. Suppress with this variable
  private boolean initialized = false;

  public SwingCheckbox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "checkbox" );
    checkBox = new JCheckBox();

    checkBox.addItemListener( new ItemListener() {
      public void itemStateChanged( ItemEvent e ) {
        SwingCheckbox.this.changeSupport.firePropertyChange( "checked", null, ( (JCheckBox) e.getSource() )
            .isSelected() );
      }
    } );

    setManagedObject( checkBox );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulCheckbox#getSelected()
   */
  public boolean getSelected() {
    // TODO Auto-generated method stub
    return checkBox.isSelected();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulCheckbox#setSelected(boolean)
   */
  public void setSelected( boolean selected ) {
    checkBox.setSelected( selected );
  }

  public void layout() {
    initialized = true;
  }

  public void setLabel( String label ) {
    checkBox.setText( label );
  }

  public String getLabel() {
    return checkBox.getText();
  }

  public boolean isChecked() {
    return checkBox.isSelected();

  }

  public boolean isDisabled() {
    return !checkBox.isEnabled();
  }

  public void setChecked( boolean checked ) {
    checkBox.setSelected( checked );
  }

  public void setDisabled( boolean dis ) {
    checkBox.setEnabled( !dis );
  }

  public void setCommand( final String method ) {
    oncommand = method;
    checkBox.addChangeListener( new ChangeListener() {
      public void stateChanged( ChangeEvent evt ) {
        if ( SwingCheckbox.this.initialized ) {
          invoke( method );
        }
      }
    } );
  }

  public String getCommand() {
    return oncommand;
  }

}
