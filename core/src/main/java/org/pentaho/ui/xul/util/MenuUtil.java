/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.util;

import org.pentaho.ui.xul.IMenuCustomization;
import org.pentaho.ui.xul.IMenuCustomization.CustomizationType;
import org.pentaho.ui.xul.IMenuCustomization.ItemType;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.containers.XulMenupopup;

public class MenuUtil {

  public static final boolean customizeMenu( XulMenubar menubar, IMenuCustomization customization, XulLoader xulLoader )
    throws XulException {

    boolean result = false;
    // see if we can find where this goes
    XulComponent anchor = menubar.getElementById( customization.getAnchorId() );
    // the anchor needs to be a XulMenuitem
    if ( anchor != null && ( anchor instanceof XulMenuitem || anchor instanceof XulMenupopup ) ) {
      // we found it
      // is this a delete?
      if ( customization.getCustomizationType() == CustomizationType.DELETE ) {
        // delete this item from its parent
        if ( anchor.getParent() instanceof XulMenu && anchor.getParent().getParent() instanceof XulMenubar ) {
          // we are deleting an item from the menubar
          // the order of these next lines is important
          anchor.getParent().getParent().removeChild( anchor.getParent() );
        } else {
          // we are removing an item from a submenu
          // the order of these next lines is important
          anchor.getParent().removeChild( anchor );
        }
        // we are done
        return true;
      } else {
        // we need a menu menu item
        XulComponent parent = null;
        int anchorIndex = -1;
        if ( customization.getCustomizationType() == CustomizationType.FIRST_CHILD
            || customization.getCustomizationType() == CustomizationType.LAST_CHILD ) {
          parent = anchor;
        } else {
          if ( anchor.getParent() instanceof XulMenu && anchor.getParent().getParent() instanceof XulMenubar ) {
            // we are adding an item to the menubar
            parent = anchor.getParent().getParent(); // the menubar
            anchorIndex = parent.getChildNodes().indexOf( anchor.getParent() ); // index of the anchor's XulMenu
            anchor = anchor.getParent();
          } else if ( anchor.getParent() instanceof XulMenupopup && anchor.getParent()
            .getParent() instanceof XulMenu ) {
            // we are adding an item to a popup menu
            parent = anchor.getParent(); // the popup menu
            anchorIndex = parent.getChildNodes().indexOf( anchor ); // index of the anchor's XulMenu
          }
        }
        // the parent needs to be a XulMenubar or XulMenupopup
        XulComponent newItem = getMenuComponent( customization, xulLoader, parent );
        // create the new item
        if ( customization.getCustomizationType() == CustomizationType.INSERT_AFTER ) {
          parent.addChildAt( newItem, anchorIndex + 1 );
          result = true;
        } else if ( customization.getCustomizationType() == CustomizationType.INSERT_BEFORE ) {
          parent.addChildAt( newItem, anchorIndex );
          result = true;
        } else if ( customization.getCustomizationType() == CustomizationType.FIRST_CHILD ) {
          parent.addChildAt( newItem, 0 );
          result = true;
        } else if ( customization.getCustomizationType() == CustomizationType.LAST_CHILD ) {
          parent.addChild( newItem );
          result = true;
        } else if ( customization.getCustomizationType() == CustomizationType.REPLACE ) {
          parent.replaceChild( anchor, newItem );
          result = true;
        }
      }
    } else {
      // we could not find where to add the menu item
      return false;
    }
    return result;
  }

  public static final XulComponent getMenuComponent( IMenuCustomization customization, XulLoader xulLoader,
      XulComponent parent ) throws XulException {
    XulComponent component = null;
    if ( customization.getItemType() == ItemType.MENU_ITEM ) {
      XulMenuitem menuItem = (XulMenuitem) xulLoader.createElement( "menuitem" ); //$NON-NLS-1$
      menuItem.setLabel( customization.getLabel() );
      menuItem.setCommand( customization.getCommand() );
      menuItem.setId( customization.getId() );
      // if we are adding this to a menubar we need to add a menu as well
      if ( parent instanceof XulMenubar ) {
        XulMenu menu = (XulMenu) xulLoader.createElement( "menu" ); //$NON-NLS-1$
        menu.setLabel( customization.getLabel() );
        menu.addChild( menuItem );
        menu.setId( customization.getId() + "-menu" ); //$NON-NLS-1$
        component = menu;
      } else {
        component = menuItem;
      }
    } else if ( customization.getItemType() == ItemType.SUBMENU ) {
      XulMenu menu = (XulMenu) xulLoader.createElement( "menu" ); //$NON-NLS-1$
      component = menu;
      menu.setLabel( customization.getLabel() );
      menu.setId( customization.getId() + "-menu" ); //$NON-NLS-1$
      XulMenupopup menuPopup = (XulMenupopup) xulLoader.createElement( "menupopup" ); //$NON-NLS-1$
      menu.addChild( menuPopup );
      menuPopup.setId( customization.getId() );
    }
    return component;
  }

}
