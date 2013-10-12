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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtMenupopup extends AbstractSwtXulContainer implements XulMenupopup {

  Menu menu = null;
  Combo menulist = null;
  XulComponent parent;
  private XulDomContainer domContainer;

  public SwtMenupopup( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menupopup" );
    this.parent = parent;
    Shell shell = null;
    this.domContainer = domContainer;

    // only build if a child of a top level menu, otherwise it will be build recursively by a parent
    if ( shell == null ) {
      XulComponent p = parent;

      while ( p != null && p instanceof XulRoot == false ) {
        if ( p instanceof XulMenubar && p.getAttributeValue( "parenttoouter" ) != null
            && p.getAttributeValue( "parenttoouter" ).equals( "true" ) && domContainer.getOuterContext() != null ) {
          shell = (Shell) domContainer.getOuterContext();
          break;
        }
        p = p.getParent();
      }
      if ( p != null && p instanceof XulRoot ) {
        shell = (Shell) ( (XulRoot) p ).getRootObject();
      }
    }

    if ( parent.getManagedObject() instanceof MenuItem ) {
      Menu flyout = new Menu( shell, SWT.DROP_DOWN );
      ( (MenuItem) parent.getManagedObject() ).setMenu( flyout );
      menu = flyout;
      setManagedObject( flyout );

    } else if ( parent instanceof XulMenuList ) {
      // not fully live, elements generated in parent layout
    } else {
      menu = new Menu( shell, SWT.POP_UP );
      setManagedObject( menu );
    }
  }

  public void layout() {
  }

  @Override
  public XulComponent getParent() {
    return parent;
  }

  public XulMenuitem createNewMenuitem() {
    return createNewMenuitemAtPos( this.getChildNodes().size() );
  }

  public XulMenuitem createNewMenuitemAtPos( int pos ) {
    if ( pos > getChildNodes().size() ) {
      pos = getChildNodes().size();
    }

    XulMenuitem item = new SwtMenuitem( this, domContainer, "menuitem", pos );
    this.addChild( item );
    return item;
  }

  @Override
  public void addChildAt( Element c, int pos ) {
    super.addChildAt( c, pos );
    if ( c instanceof SwtMenuitem ) {
      ( (SwtMenuitem) c ).reposition( pos );
    }
  }

  //
  //
  // @Override
  // public void addChild(Element c) {
  // super.addChild(c);
  // if(c instanceof SwtMenuitem){
  // ((SwtMenuitem) c).reposition(getChildNodes().size()-1);
  // }
  // }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );
    if ( menu != null ) {
      this.menu.setVisible( visible );
    }
  }

  @Override
  public void setDisabled( boolean disabled ) {
    super.setDisabled( disabled );
    if ( menu != null ) {
      for ( MenuItem item : menu.getItems() ) {
        item.setEnabled( !disabled );
      }
    }
  }

}
