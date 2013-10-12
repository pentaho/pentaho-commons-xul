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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.Orient;

public class SwtMenu extends AbstractSwtXulContainer implements XulMenu {

  private Menu menu;

  private String accel = null;
  private MenuItem header;
  private Menu dropdown;
  private Shell shell;

  private XulComponent parent;
  private XulDomContainer domContainer;

  public SwtMenu( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menu" );
    this.domContainer = domContainer;
    if ( domContainer.getOuterContext() != null ) {
      shell = (Shell) domContainer.getOuterContext();
    }

    orient = Orient.VERTICAL;

    // only build if a child of a top level menu, otherwise it will be build recursively by a parent
    if ( shell == null ) {
      XulComponent p = parent;
      while ( p != null && p instanceof XulRoot == false ) {
        p = p.getParent();
      }
      if ( p != null && p instanceof XulRoot ) {
        shell = (Shell) p.getManagedObject();
      }
    }
    // if(parent.getManagedObject() != null){
    header = new MenuItem( (Menu) parent.getManagedObject(), SWT.CASCADE );
    setManagedObject( header );
    // }
    this.parent = parent;
  }

  @Override
  public XulComponent getParent() {
    return parent;
  }

  private void addMenuChildren( Menu menuParent, List<XulComponent> children ) {
    for ( Element comp : children ) {

      for ( XulComponent compInner : ( (SwtMenupopup) comp ).getChildNodes() ) {
        if ( compInner instanceof XulMenu ) {
          MenuItem item = new MenuItem( menuParent, SWT.CASCADE );
          Menu flyout = new Menu( shell, SWT.DROP_DOWN );
          item.setMenu( flyout );
          addMenuChildren( flyout, compInner.getChildNodes() );
        } else {

        }
      }

    }
  }

  public void layout() {
    initialized = true;
  }

  //
  // @Override
  // public void addComponent(XulComponent c) {
  // super.addComponent(c);
  // if (initialized) {
  // resetContainer();
  // layout();
  // }
  // }

  public String getAcceltext() {
    return accel;
  }

  public String getAccesskey() {
    return String.valueOf( header.getAccelerator() );
  }

  public boolean isDisabled() {
    return !header.isEnabled();
  }

  public String getLabel() {
    return header.getText();
  }

  public void setAcceltext( String accel ) {
    this.accel = accel;
  }

  public void setAccesskey( String accessKey ) {
    if ( header != null ) {
      int mask = 0;
      if ( accessKey.indexOf( "ctrl" ) > -1 ) {
        mask += SWT.CTRL;
      }
      if ( accessKey.indexOf( "shift" ) > -1 ) {
        mask += SWT.SHIFT;
      }
      if ( accessKey.indexOf( "alt" ) > -1 ) {
        mask += SWT.ALT;
      }
      String remainder =
          accessKey.replaceAll( "ctrl", "" ).replaceAll( "shift", "" ).replaceAll( "alt", "" ).replaceAll( "-", "" )
              .trim();
      if ( remainder.length() == 1 ) {
        mask += remainder.charAt( 0 );
      }

      header.setAccelerator( mask );
    }
  }

  public void setDisabled( boolean disabled ) {
    if ( header != null ) {
      header.setEnabled( !disabled );
    }
  }

  public void setLabel( String label ) {
    if ( header != null ) {
      header.setText( label );
    }
  }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );
    header.setEnabled( visible );
  }

}
