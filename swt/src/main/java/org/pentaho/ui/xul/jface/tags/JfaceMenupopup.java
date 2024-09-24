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

package org.pentaho.ui.xul.jface.tags;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class JfaceMenupopup extends AbstractSwtXulContainer implements XulMenupopup {

  IMenuManager menu = null;
  XulComponent parent;
  private XulDomContainer domContainer;

  public JfaceMenupopup( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menupopup" );
    this.parent = parent;
    this.domContainer = domContainer;

    if ( parent instanceof JfaceMenu ) {
      Object grampy = parent.getParent();
      if ( grampy instanceof JfaceMenubar ) {
        menu = (IMenuManager) parent.getManagedObject();
        this.setManagedObject( menu );
      } else if ( grampy instanceof JfaceMenupopup ) {
        menu = (IMenuManager) parent.getManagedObject();
        this.setManagedObject( menu );
      }
    } else {
      // this is for popup menus
      menu = new MenuManager();
      this.setManagedObject( menu );
    }
    if ( menu == null ) {
      // System.out.println("JfaceMenupopup constructor bad parent");
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

    /*
     * Action action = new Action("tmp name") { public void run() { String command = JfaceMenupopup.this.onCommand;
     * if(command != null){ invoke(command); } } };
     * 
     * String id="menuitem-"+menuItemSerial; menuItemSerial++; action.setId(id);
     */
    XulMenuitem item = new JfaceMenuitem( null, this, domContainer, "menuitem", pos );

    // action.setId(id);

    // item.setManagedObject( action );
    /*
     * if( pos < getChildNodes().size() ) { String anchorId = menu.getItems()[pos].getId(); if( anchorId == null ) {
     * System.out.println("can't find anchor id for insert at "+pos); menu.add(action); super.addChild(item); } else {
     * menu.insertBefore(anchorId, action); super.addChildAt(item, pos); } } else { menu.add(action);
     * super.addChild(item); }
     */
    return item;
  }

  @Override
  public void addChild( Element c ) {
    addChildAt( c, -1 );
  }

  @Override
  public void addChildAt( Element c, int pos ) {

    if ( pos != -1 ) {
      super.addChildAt( c, pos );
    } else {
      super.addChild( c );
    }

    /*
     * if(c instanceof JfaceMenuseparator){ JfaceMenuseparator sep = (JfaceMenuseparator) c; if( pos == -1 ) { menu.add(
     * (CustomSeparator) sep.getManagedObject() ); } }
     */

    // see if we have this already
    IContributionItem[] items = menu.getItems();
    for ( IContributionItem item : items ) {
      if ( item.getId() != null && item.getId().equals( c.getAttributeValue( "ID" ) ) && !item.isVisible() ) {
        // we already have this, just show it
        item.setVisible( true );
        // System.out.println("JfaceMenupopup making existing item visible "+item.getId());
        return;
      }
    }

    IAction action = null;
    IContributionItem contribs = null;
    if ( c instanceof JfaceMenuitem ) {
      JfaceMenuitem item = (JfaceMenuitem) c;
      Object man = item.getManagedObject();
      if ( man instanceof IAction ) {
        action = (IAction) item.getManagedObject();
        // System.out.println("JfaceMenupopup appending menu item "+action.getText());
      } else if ( man instanceof IContributionItem ) {
        contribs = (IContributionItem) man;
      } else if ( man == null ) {
        // System.out.println("JfaceMenupopup appending null item ");
      } else {
        // System.out.println("JfaceMenupopup appending unknown type "+man.getClass().getName());
      }
    } else if ( c instanceof JfaceMenu ) {
      JfaceMenu item = (JfaceMenu) c;
      contribs = item.getJfaceMenu();
      // System.out.println("JfaceMenupopup appending menu "+contribs != null ? contribs.getId() :
      // c.getAttributeValue("ID"));
    }
    if ( action == null && contribs == null ) {
      // should not get here
      return;
    }

    String id = null;
    if ( pos == 0 && pos < menu.getItems().length ) {
      id = menu.getItems()[1].getId();
    } else if ( pos > 0 && pos < menu.getItems().length ) {
      id = menu.getItems()[pos].getId();
    }
    if ( id != null ) {
      if ( action != null ) {
        menu.insertBefore( id, action );
      } else {
        menu.insertBefore( id, contribs );
      }
    } else {
      if ( action != null ) {
        menu.add( action );
      } else {
        menu.add( contribs );
      }
    }

    /*
     * else if(c instanceof JfaceMenuitem){
     * 
     * JfaceMenuitem item = (JfaceMenuitem) c;
     * 
     * item.createItem(item, this, pos, true);
     * 
     * Action action = new Action(((JfaceMenuitem) c).getLabel()) { public void run() { String command =
     * JfaceMenupopup.this.onCommand; if(command != null){ invoke(command); } } };
     * 
     * XulMenuitem item = new JfaceMenuitem(this, c, domContainer, "menuitem", pos); String id = ((JfaceMenuitem)
     * c).getId(); if( id == null ) { id="menuitem-"+menuItemSerial; menuItemSerial++; } item.setId(id);
     * action.setId(id);
     */

    // item.setManagedObject( action );
    /*
     * if(pos > getChildNodes().size()){ pos = getChildNodes().size(); }
     * 
     * if( pos < getChildNodes().size() ) { if( pos >= menu.getItems().length ) {
     * System.out.println("hmm..."+menu.getItems().length); } String anchorId = menu.getItems()[pos].getId(); if(
     * anchorId == null ) { System.out.println("can't find anchor id for "+((JfaceMenuitem) c).getLabel());
     * menu.add(action); super.addChild(c); } else { menu.insertBefore(anchorId, action); super.addChildAt(c, pos); } }
     * else { super.addChild(c); menu.add(action); } }
     */
  }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );
    if ( menu != null ) {
      this.menu.setVisible( visible );
      for ( IContributionItem item : menu.getItems() ) {
        item.setVisible( visible );
      }
    }
  }

  @Override
  public void setDisabled( boolean disabled ) {
    super.setDisabled( disabled );
  }

  @Override
  public void removeChild( Element e ) {
    if ( e instanceof JfaceMenuitem ) {
      ( (JfaceMenuitem) e ).setVisible( false );
    }
    if ( e instanceof JfaceMenupopup ) {
      ( (JfaceMenupopup) e ).setVisible( false );
    }
    if ( e instanceof JfaceMenu ) {
      ( (JfaceMenu) e ).setVisible( false );
    }
  }

  public void removeChildren() {
    menu.removeAll();
  }
}
