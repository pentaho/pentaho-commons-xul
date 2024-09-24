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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class JfaceMenubar extends AbstractSwtXulContainer implements XulMenubar {

  private MenuManager menuManager = null;
  private static Log logger = LogFactory.getLog( JfaceMenubar.class );
  private XulComponent parent;

  public JfaceMenubar( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menubar" );

    ApplicationWindow window = ApplicationWindowLocal.getApplicationWindow();

    if ( window == null ) {
      logger.error( "No ApplicationWindow available via ApplicationWindowLocal" );
    } else {
      menuManager = window.getMenuBarManager();
    }

    logger.debug( "JfaceMenubar grabbed window's MenuManager: " + menuManager );
    // System.out.println("JfaceMenubar grabbed window's MenuManager: "+menuManager);
    setManagedObject( menuManager );
    this.parent = parent;
  }

  @Override
  public XulComponent getParent() {
    return parent;
  }

  @Override
  public void addChild( Element c ) {
    addChildAt( c, -1 );
    /*
     * super.addChild(c); System.out.println("JfaceMenubar appending child "+c.getAttributeValue("id")); if( c
     * instanceof JfaceMenu) { menuManager.add(((JfaceMenu) c).getJfaceMenu() ); } if( c instanceof JfaceMenuitem) {
     * menuManager.add(((JfaceMenuitem) c).getAction() ); }
     */
  }

  @Override
  public void addChildAt( Element c, int pos ) {

    if ( pos != -1 ) {
      super.addChildAt( c, pos );
    } else {
      super.addChild( c );
    }

    if ( menuManager == null ) {
      // there is nothing else to do
      return;
    }
    // see if we have this already
    IContributionItem[] items = menuManager.getItems();
    for ( IContributionItem item : items ) {
      if ( item.getId() != null && item.getId().equals( c.getAttributeValue( "ID" ) ) && !item.isVisible() ) {
        // we already have this, just show it
        item.setVisible( true );
        // System.out.println("JfaceMenubar making existing item visible "+item.getId());
        return;
      }
    }

    MenuManager item = ( (JfaceMenu) c ).getJfaceMenu();
    // System.out.println("JfaceMenubar adding child "+c.getAttributeValue("id")+" at "+pos);
    if ( c instanceof JfaceMenu ) {
      if ( pos == 0 && pos < menuManager.getItems().length ) {
        String id = menuManager.getItems()[1].getId();
        menuManager.insertBefore( id, item );
      } else if ( pos > 0 && pos < menuManager.getItems().length ) {
        String id = menuManager.getItems()[pos].getId();
        menuManager.insertBefore( id, item );
      } else {
        menuManager.add( item );
      }
    }
  }

  @Override
  public void layout() {
    // for (XulComponent comp : children) {
    // if (comp instanceof SwtMenu) {
    // this.menuBar.add((JMenu) ((SwingMenu) comp).getManagedObject());
    //
    // }
    // }
    initialized = true;
  }

  @Override
  public void removeChild( Element e ) {

    // System.out.println( "JfaceMenubar.removeChild: "+e.getClass().getSimpleName() );
    if ( e instanceof JfaceMenuitem ) {
      // System.out.println( "removing menu item: "+((JfaceMenuitem)e).getLabel() );
      ( (JfaceMenuitem) e ).setVisible( false );
    }
    if ( e instanceof JfaceMenupopup ) {
      // System.out.println( "removing menu popup: "+((JfaceMenupopup)e).getId() );
      ( (JfaceMenupopup) e ).setVisible( false );
    }
    if ( e instanceof JfaceMenu ) {
      // System.out.println( "removing menu: "+((JfaceMenu)e).getId() );
      ( (JfaceMenu) e ).setVisible( false );
      /*
       * for( IContributionItem item : menuManager.getItems() ) { if( item.getId() != null &&
       * item.getId().equals(((XulMenu)e).getId()) ) { menuManager.remove(item); } }
       */
    }
  }

}
