package org.pentaho.ui.xul.util;

import java.util.List;

import org.pentaho.platform.api.ui.IMenuCustomization;
import org.pentaho.platform.api.ui.IMenuCustomization.CustomizationType;
import org.pentaho.platform.api.ui.IMenuCustomization.ItemType;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulLoader;

public class MenuUtil {

	  public static final boolean customizeMenu( XulMenubar menubar, List<IMenuCustomization> customizations, XulLoader xulLoader ) {
		  
		  boolean result = false;
		  // handle each customization at a time, allowing them to build on each other
		  for( IMenuCustomization customization : customizations ) {
			  try {
				  // see if we can find where this goes
				  XulComponent anchor = menubar.getElementById( customization.getAnchorId() );
				  // the anchor needs to be a XulMenuitem
				  if( anchor != null && (anchor instanceof XulMenuitem || anchor instanceof XulMenupopup ) ) {
					  // we found it
					  // is this a delete?
					  if( customization.getCustomizationType() == CustomizationType.DELETE ) {
						  // delete this item from its parent
						  if( anchor.getParent() instanceof XulMenu && anchor.getParent().getParent() instanceof XulMenubar ) {
							  // we are deleting an item from the menubar
							  // the order of these next lines is important
							  ((XulContainer)anchor.getParent().getParent()).removeComponent( anchor.getParent() );
							  anchor.getParent().getParent().removeChild( anchor.getParent() );
						  } else {
							  // we are removing an item from a submenu
							  // the order of these next lines is important
							  ((XulContainer)anchor.getParent()).removeComponent( anchor );
							  anchor.getParent().removeChild( anchor );
						  }
						  result = true;
						  continue;
						  // we are done
					  } else {
						  // we need a menu menu item
						  XulComponent parent = null;
						  int anchorIndex = -1;
						  if( customization.getCustomizationType() == CustomizationType.FIRST_CHILD ||
								  customization.getCustomizationType() == CustomizationType.LAST_CHILD ) {
							  if( anchor.getParent() instanceof XulMenu ) {
								  // we need to find the XulMenupop
								  parent = (XulMenupopup) anchor;
							  } else {
								  parent = anchor;
							  }
						  } else {
							  if( anchor.getParent() instanceof XulMenu && anchor.getParent().getParent() instanceof XulMenubar ) {
								  // we are adding an item to the menubar
								  parent = anchor.getParent().getParent(); // the menubar
								  anchorIndex = parent.getChildNodes().indexOf( anchor.getParent() ); // index of the anchor's XulMenu
								  anchor = anchor.getParent();
							  }
							  else if ( anchor.getParent() instanceof XulMenupopup && anchor.getParent().getParent() instanceof XulMenu ) {
								  // we are adding an item to a popup menu
								  parent = anchor.getParent(); // the popup menu
								  anchorIndex = parent.getChildNodes().indexOf( anchor ); // index of the anchor's XulMenu
							  }
						  }
						  // the parent needs to be a XulMenubar or XulMenupopup
						  XulComponent newItem = getMenuComponent( customization, xulLoader, parent );
						  // create the new item
						  if( customization.getCustomizationType() == CustomizationType.INSERT_AFTER ) {
							  if( parent instanceof XulContainer ) {
								  ((XulContainer) parent).addComponentAt( newItem, anchorIndex+1 );
							  }
							  parent.addChildAt(newItem, anchorIndex+1 ); 
							  result = true;
						  }
						  else if( customization.getCustomizationType() == CustomizationType.INSERT_BEFORE ) {
							  if( parent instanceof XulContainer ) {
								  ((XulContainer) parent).addComponentAt( newItem, anchorIndex );
							  }
							  parent.addChildAt(newItem, anchorIndex ); 
							  result = true;
						  }
						  else if( customization.getCustomizationType() == CustomizationType.FIRST_CHILD ) {
							  if( parent instanceof XulContainer ) {
								  ((XulContainer) parent).addComponentAt( newItem, 0 );
							  }
							  parent.addChildAt( newItem, 0 ); 
							  result = true;
						  }
						  else if( customization.getCustomizationType() == CustomizationType.LAST_CHILD ) {
							  if( parent instanceof XulContainer ) {
								  ((XulContainer) parent).addComponent( newItem );
							  }
							  parent.addChild( newItem ); 
							  result = true;
						  }
						  else if( customization.getCustomizationType() == CustomizationType.REPLACE ) {
							  parent.replaceChild( anchor, newItem); 
							  result = true;
						  }
					  }
				  } else {
					  // we could not find where to add the menu item
					  // TODO log this
				  }
			  } catch (Exception e) {
				  // TODO log this
				  e.printStackTrace();
			  }
		  }
		  return result;
	  }

	  public static final XulComponent getMenuComponent( IMenuCustomization customization, XulLoader xulLoader, XulComponent parent ) {
		  XulComponent component = null;
		  try {
			  if( customization.getItemType() == ItemType.MENU_ITEM ) {
				  XulMenuitem menuItem = (XulMenuitem) xulLoader.createElement("menuitem");
				  menuItem.setLabel( customization.getLabel() );
				  menuItem.setCommand( customization.getCommand() );
				  menuItem.setId( customization.getId() );
				  menuItem.setID( customization.getId() );
				  // if we are adding this to a menubar we need to add a menu as well
				  if( parent instanceof XulMenubar ) {
					  XulMenu menu = (XulMenu) xulLoader.createElement("menu");
					  menu.setLabel( customization.getLabel() );
					  menu.addChild( menuItem );
					  menu.addComponent( menuItem );
					  menu.setId( customization.getId()+"-menu" );
					  menu.setID( customization.getId()+"-menu" );
					  component = menu;
				  } else {
					  component = menuItem;
				  }
			  } 
			  else if( customization.getItemType() == ItemType.SUBMENU ) {
				  XulMenu menu = (XulMenu) xulLoader.createElement("menu");
				  component = menu;
				  menu.setLabel( customization.getLabel() );
				  menu.setId( customization.getId()+"-menu" );
				  menu.setID( customization.getId()+"-menu" );
				  XulMenupopup menuPopup = (XulMenupopup) xulLoader.createElement("menupopup");
				  menu.addChild( menuPopup );
				  menu.addComponent( menuPopup );
				  menuPopup.setId( customization.getId() );
				  menuPopup.setID( customization.getId() );
			  }
				  
		  } catch (Exception e) {
			  // TODO log this
			  e.printStackTrace();
		  }
		  return component;
	  }
	  
}
