/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.ui.xul.test.html;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.IMenuCustomization.CustomizationType;
import org.pentaho.ui.xul.IMenuCustomization.ItemType;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.html.HtmlXulLoader;
import org.pentaho.ui.xul.html.IHtmlElement;
import org.pentaho.ui.xul.test.html.HtmlHarness;
import org.pentaho.ui.xul.util.MenuCustomization;
import org.pentaho.ui.xul.util.MenuUtil;

import junit.framework.TestCase;

public class CustomizeMenuTest extends TestCase {

	private XulMenubar menubar; // we manipulate this menubar in each test
	
	private XulLoader xulLoader;
	
	private XulDomContainer getStartDom() {
	    try{
		      InputStream in = HtmlHarness.class.getClassLoader().getResourceAsStream("documents/htmlmenutest.xul"); //$NON-NLS-1$

		      assertNotNull( "Test document input stream is null", in ); //$NON-NLS-1$

		      
		      SAXReader rdr = new SAXReader();
		      final Document doc = rdr.read(in);
		      
		      HtmlXulLoader loader = new HtmlXulLoader();
		      // check that get instance works
		      loader = (HtmlXulLoader) loader.getNewInstance();

		      XulDomContainer container = loader.loadXul(doc);
		      assertNotNull( "Test document could not be read", container ); //$NON-NLS-1$
		      return container;

		    } catch(Exception e){
		      e.printStackTrace();
		    }
		    return null;
	}
	
	private void setupMenubar() {
	      XulDomContainer container = getStartDom();

	      List<XulComponent> menubars = container.getDocumentRoot().getElementsByTagName( "menubar" ); //$NON-NLS-1$
	      assertNotNull( "Menubar list is null", menubars ); //$NON-NLS-1$
	      assertEquals( "1 menubar expected", 1, menubars.size() ); //$NON-NLS-1$
	      assertTrue( "Menubar not found", menubars.get(0) instanceof XulMenubar ); //$NON-NLS-1$
	      menubar = (XulMenubar) menubars.get(0);
	      try {
		      xulLoader = new HtmlXulLoader();
	      } catch (Exception e) {
	    	  
	      }
	}
	
	public void testInvalidAnchor() throws Exception {
    	setupMenubar();
	    String itemId = "item0"; //$NON-NLS-1$
	    MenuCustomization custom = new MenuCustomization( itemId, "bogus-anchor-id", "item 0", "", ItemType.MENU_ITEM, CustomizationType.INSERT_BEFORE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	    boolean result = MenuUtil.customizeMenu(menubar, custom, xulLoader);
	    assertTrue( "customization should have failed", result == false ); //$NON-NLS-1$
	}
	
	public void testMenubarItemInsertBefore() throws Exception {
    	setupMenubar();
	      String itemId = "new-item"; //$NON-NLS-1$
	      MenuCustomization custom = new MenuCustomization( );
	      custom.setId( itemId );
	      custom.setAnchorId( "menubar-item-2" ); //$NON-NLS-1$
	      custom.setLabel( "item 0" ); //$NON-NLS-1$
	      custom.setCommand( "command1" ); //$NON-NLS-1$
	      custom.setItemType( ItemType.MENU_ITEM );
	      custom.setCustomizationType( CustomizationType.INSERT_BEFORE );
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//	      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );
	      
	      XulComponent validate = menubar.getElementByXPath( "menu[2]/menuitem[1]" ); //$NON-NLS-1$
	      assertNotNull( "Menu item not found where exected", validate ); //$NON-NLS-1$
	      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem ); //$NON-NLS-1$
	      assertEquals( "Customized item not where expected", itemId, validate.getId() ); //$NON-NLS-1$

	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Could not find item in HTML", html.indexOf( "<a id=\"new-item-menu\" href=\"command1\">item 0</a>" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'new-item-menu';" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public void testMenubarItemInsertAfter() throws Exception {
    	setupMenubar();
	      String itemId = "new-item"; //$NON-NLS-1$
	      MenuCustomization custom = new MenuCustomization( itemId, "menubar-item-2", "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.INSERT_AFTER ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//        System.out.println( ( (Element) menubar.getElementObject() ).asXML() );
	      
	      XulComponent validate = menubar.getElementByXPath( "menu[3]/menuitem[1]" ); //$NON-NLS-1$
	      assertNotNull( "Menu item not found where exected", validate ); //$NON-NLS-1$
	      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem ); //$NON-NLS-1$
	      assertEquals( "Customized item not where expected", itemId, validate.getId() ); //$NON-NLS-1$

	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Could not find item in HTML", html.indexOf( "<a id=\"new-item-menu\" href=\"command1\">item 0</a>" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'new-item-menu';" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$

	}
	
	public void testMenubarItemDeleteFromFront() throws Exception {
    	setupMenubar();
	      String itemToDeleteId = "menubar-item-to-delete1"; //$NON-NLS-1$

	      XulComponent validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item to delete not found", validate ); //$NON-NLS-1$

	      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNull( "Item was not deleted", validate ); //$NON-NLS-1$

	      // first item should no longer be there
	      validate = menubar.getElementByXPath( "menu[1]/menuitem[1]" ); //$NON-NLS-1$
	      assertNotNull( "Menu item not found where exected", validate ); //$NON-NLS-1$
	      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem ); //$NON-NLS-1$
	      assertTrue( "Deleted item still exists", !validate.getId().equals( itemToDeleteId ) ); //$NON-NLS-1$

	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Should not have found item in HTML", html.indexOf( "<a id=\"menu1\"" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Should not have found item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'menu1';" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      
	}

	public void testMenubarItemDeleteFromMiddle() throws Exception {
    	setupMenubar();
	      String itemToDeleteId = "menubar-item-to-delete2"; //$NON-NLS-1$

	      XulComponent validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item to delete not found", validate ); //$NON-NLS-1$

	      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNull( "Item was not deleted", validate ); //$NON-NLS-1$

	      // first item should no longer be there
	      validate = menubar.getElementByXPath( "menu[3]/menuitem[1]" ); //$NON-NLS-1$
	      assertNotNull( "Menu item not found where exected", validate ); //$NON-NLS-1$
	      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem ); //$NON-NLS-1$
	      assertTrue( "Deleted item still exists", !validate.getId().equals( itemToDeleteId ) ); //$NON-NLS-1$

	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Should not have found item in HTML", html.indexOf( "<a id=\"menu3\"" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Should not have found item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'menu3';" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$

	}

	public void testMenubarItemDeleteFromEnd() throws Exception {
    	setupMenubar();
	      String itemToDeleteId = "menubar-item-to-delete3"; //$NON-NLS-1$

	      XulComponent validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item to delete not found", validate ); //$NON-NLS-1$

	      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNull( "Item was not deleted", validate ); //$NON-NLS-1$

	      // first item should no longer be there
	      validate = menubar.getElementByXPath( "menu[7]" ); //$NON-NLS-1$
	      assertNull( "Deleted item still exists", validate ); //$NON-NLS-1$

	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Should not have found item in HTML", html.indexOf( "<a id=\"menu7\"" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Should not have found item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'menu7';" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$

	}
	
	public void testMenubarItemReplace() throws Exception {
    	setupMenubar();
	      String itemToReplaceId = "menubar-item-to-replace"; //$NON-NLS-1$
	      String newItemId = "new-menubar-item"; //$NON-NLS-1$

	      XulComponent validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToReplaceId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item to replace not found", validate ); //$NON-NLS-1$

	      MenuCustomization custom = new MenuCustomization( newItemId, itemToReplaceId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.REPLACE ); //$NON-NLS-1$ //$NON-NLS-2$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToReplaceId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNull( "Item was not replaced", validate ); //$NON-NLS-1$

	      validate = menubar.getElementByXPath( "menu[4]/menuitem[@ID='"+newItemId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Customized item not added", validate ); //$NON-NLS-1$
	      
	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Should not have found item in HTML", html.indexOf( "<a id=\"menu4\"" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Should not have found item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'menu4';" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in HTML", html.indexOf( "<a id=\"new-menubar-item-menu\" href=\"command1\">item 0</a>" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'new-menubar-item-menu';" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$

	}

 	public void testSubmenuItemInsertBefore() throws Exception {
    	setupMenubar();
	      String itemId = "new-item"; //$NON-NLS-1$
	      MenuCustomization custom = new MenuCustomization( itemId, "submenu-item-1", "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.INSERT_BEFORE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );
	      
	      XulComponent validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[2]" ); //$NON-NLS-1$
	      assertNotNull( "Menu item not found where exected", validate ); //$NON-NLS-1$
	      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem ); //$NON-NLS-1$
	      assertEquals( "Customized item not where expected", itemId, validate.getId() ); //$NON-NLS-1$

	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"item 0\", \"command1\");" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$

	}

	public void testSubmenuItemInsertAfter() throws Exception {
    	setupMenubar();
	      String itemId = "new-item"; //$NON-NLS-1$
	      MenuCustomization custom = new MenuCustomization( itemId, "submenu-item-1", "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.INSERT_AFTER ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );
	      
	      XulComponent validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[3]" ); //$NON-NLS-1$
	      assertNotNull( "Menu item not found where exected", validate ); //$NON-NLS-1$
	      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem ); //$NON-NLS-1$
	      assertEquals( "Customized item not where expected", itemId, validate.getId() ); //$NON-NLS-1$

	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"item 0\", \"command1\");" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public void testSubmenuItemDeleteFromFront() throws Exception {
    	setupMenubar();
	      String itemToDeleteId = "submenu-item-to-delete1"; //$NON-NLS-1$

	      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item to delete not found", validate ); //$NON-NLS-1$

	      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNull( "Item was not deleted", validate ); //$NON-NLS-1$

	      // first item should no longer be there
	      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[1]" ); //$NON-NLS-1$
	      assertNotNull( "Menu item not found where exected", validate ); //$NON-NLS-1$
	      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem ); //$NON-NLS-1$
	      assertTrue( "Deleted item still exists", !validate.getId().equals( itemToDeleteId ) ); //$NON-NLS-1$
	      
	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Should not have found item in script", html.indexOf( "menu.addItem(\"item6-1\", \"\");" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      
	}

	public void testSubmenuItemDeleteFromMiddle() throws Exception {
    	setupMenubar();
	      String itemToDeleteId = "submenu-item-to-delete2"; //$NON-NLS-1$

	      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item to delete not found", validate ); //$NON-NLS-1$

	      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNull( "Item was not deleted", validate ); //$NON-NLS-1$

	      // first item should no longer be there
	      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[3]" ); //$NON-NLS-1$
	      assertNotNull( "Menu item not found where exected", validate ); //$NON-NLS-1$
	      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem ); //$NON-NLS-1$
	      assertTrue( "Deleted item still exists", !validate.getId().equals( itemToDeleteId ) ); //$NON-NLS-1$
	      
	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Should not have found item in script", html.indexOf( "menu.addItem(\"item6-3\", \"\");" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$

	}


	public void testSubmenuItemDeleteFromEnd() throws Exception {
    	setupMenubar();
	      String itemToDeleteId = "submenu-item-to-delete3"; //$NON-NLS-1$

	      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item to delete not found", validate ); //$NON-NLS-1$

	      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNull( "Item was not deleted", validate ); //$NON-NLS-1$

	      // first item should no longer be there
	      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[6]" ); //$NON-NLS-1$
	      assertNull( "Deleted item still exists", validate ); //$NON-NLS-1$
	      
	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Should not have found item in script", html.indexOf( "menu.addItem(\"item6-6\", \"\");" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      
	}

	public void testSubmenuItemReplace() throws Exception {
    	setupMenubar();
	      String itemToReplaceId = "submenu-item-to-replace"; //$NON-NLS-1$
	      String newItemId = "new-submenu-item"; //$NON-NLS-1$

	      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToReplaceId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item to replace not found", validate ); //$NON-NLS-1$

	      MenuCustomization custom = new MenuCustomization( newItemId, itemToReplaceId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.REPLACE ); //$NON-NLS-1$ //$NON-NLS-2$
	      
	      MenuUtil.customizeMenu(menubar, custom, xulLoader);
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToReplaceId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNull( "Item was not replaced", validate ); //$NON-NLS-1$

	      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[@ID='"+newItemId+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Customized item not added", validate ); //$NON-NLS-1$

	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Should not have found item in script", html.indexOf( "menu.addItem(\"item6-4\", \"\");" ) == -1 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"item 0\", \"command1\");" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$

	}
	
	public void testNewSubmenu() throws Exception {
    	setupMenubar();
	      String menuId = "new-menu"; //$NON-NLS-1$
	      String newItemId1 = "new-item1"; //$NON-NLS-1$
	      String newItemId2 = "new-item2"; //$NON-NLS-1$
	      String newItemId3 = "new-item3"; //$NON-NLS-1$

	      MenuUtil.customizeMenu(menubar, new MenuCustomization( menuId, "menubar-item-2", "new menu", "", ItemType.SUBMENU, CustomizationType.INSERT_BEFORE ), xulLoader); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      MenuUtil.customizeMenu(menubar, new MenuCustomization( newItemId1, menuId, "new item 1", "command1", ItemType.MENU_ITEM, CustomizationType.FIRST_CHILD ), xulLoader); //$NON-NLS-1$ //$NON-NLS-2$
	      MenuUtil.customizeMenu(menubar, new MenuCustomization( newItemId2, newItemId1, "new item 2", "command2", ItemType.MENU_ITEM, CustomizationType.INSERT_AFTER ), xulLoader); //$NON-NLS-1$ //$NON-NLS-2$
	      MenuUtil.customizeMenu(menubar, new MenuCustomization( newItemId3, menuId, "new item 3", "command3", ItemType.MENU_ITEM, CustomizationType.LAST_CHILD ), xulLoader); //$NON-NLS-1$ //$NON-NLS-2$
	      
//      System.out.println( ( (Element) menubar.getElementObject() ).asXML() );

	      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+newItemId1+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item was not added", validate ); //$NON-NLS-1$

	      validate = menubar.getElementByXPath( "//menuitem[@ID='"+newItemId2+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item was not added", validate ); //$NON-NLS-1$

	      validate = menubar.getElementByXPath( "//menuitem[@ID='"+newItemId3+"']" ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertNotNull( "Item was not added", validate ); //$NON-NLS-1$

	      validate = menubar.getElementByXPath( "menu[2]/menupopup[1]/menuitem[1]" ); //$NON-NLS-1$
	      assertNotNull( "Customized item not added", validate ); //$NON-NLS-1$
	      assertTrue( "Customized item not added", newItemId1.equals( validate.getId() ) ); //$NON-NLS-1$
	      
	      validate = menubar.getElementByXPath( "menu[2]/menupopup[1]/menuitem[2]" ); //$NON-NLS-1$
	      assertNotNull( "Customized item not added", validate ); //$NON-NLS-1$
	      assertTrue( "Customized item not added", newItemId2.equals( validate.getId() ) ); //$NON-NLS-1$
	      
	      validate = menubar.getElementByXPath( "menu[2]/menupopup[1]/menuitem[3]" ); //$NON-NLS-1$
	      assertNotNull( "Customized item not added", validate ); //$NON-NLS-1$
	      assertTrue( "Customized item not added", newItemId3.equals( validate.getId() ) ); //$NON-NLS-1$
	      		      
	      StringBuilder sb = new StringBuilder();
	      ((IHtmlElement) menubar ).getHtml(sb);
	      String html = sb.toString();
	      // we should see an <a> tag for the menubar item
//	      System.out.println( html );
	      assertTrue( "Could not find item in HTML", html.indexOf( "<a id=\"new-menu-menu\" href=\"#\">new menu</a>" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in script", html.indexOf( "var menu = ms.addMenu(document.getElementById(\"new-menu-menu\"));" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"new item 1\", \"command1\");" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"new item 2\", \"command2\");" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"new item 3\", \"command3\");" ) > 0 ); //$NON-NLS-1$ //$NON-NLS-2$

	}

	public void testMenuUtilObject() {
		// this is mainly for code coverage, all the methods are static
		new MenuUtil();
	}

}
