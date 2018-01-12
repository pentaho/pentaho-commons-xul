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

package org.pentaho.ui.xul.swing.tags;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

public class SwingMenu extends AbstractSwingContainer implements XulMenu {

  private JMenu menu;

  private String accel = null;

  public SwingMenu( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menu" );

    menu = new JMenu();
    setManagedObject( menu );

  }

  public void layout() {
    for ( Element comp : getChildNodes() ) {
      if ( comp instanceof SwingMenupopup ) {

        for ( XulComponent compInner : comp.getChildNodes() ) {
          if ( compInner instanceof XulMenuseparator ) {
            this.menu.addSeparator();
          } else if ( compInner instanceof SwingMenu ) {
            this.menu.add( (JMenu) ( compInner ).getManagedObject() );
          } else {
            this.menu.add( (JMenuItem) ( compInner ).getManagedObject() );
          }
        }
      }
    }
    initialized = true;
  }

  public String getAcceltext() {
    return accel;
  }

  public String getAccesskey() {
    return String.valueOf( menu.getText().charAt( menu.getDisplayedMnemonicIndex() ) );
  }

  public boolean isDisabled() {
    return !menu.isEnabled();
  }

  public String getLabel() {
    return menu.getText();
  }

  public void setAcceltext( String accel ) {
    this.accel = accel;
    // menu.setAccelerator(KeyStroke.getKeyStroke(accel));
  }

  public void setAccesskey( String accessKey ) {
    if ( accessKey == null || accessKey.length() == 0 ) {
      menu.setMnemonic( 0 );
    } else {
      menu.setMnemonic( accessKey.charAt( 0 ) );
    }
  }

  public void setDisabled( boolean disabled ) {
    menu.setEnabled( !disabled );
  }

  public void setLabel( String label ) {
    menu.setText( label );
  }

  public void resetContainer() {
    menu.removeAll();
  }
}
