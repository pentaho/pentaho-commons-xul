package org.pentaho.ui.xul.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.pentaho.platform.api.ui.IMenuCustomization;
import org.pentaho.platform.api.ui.IMenuCustomization.CustomizationType;
import org.pentaho.platform.api.ui.IMenuCustomization.ItemType;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulLoader;
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
		      InputStream in = HtmlHarness.class.getClassLoader().getResourceAsStream("resource/documents/menutest3.xul");

		      if(in == null){
		        System.out.println("Input is null");
		        System.exit(123);
		      }

		      SAXReader rdr = new SAXReader();
		      final Document doc = rdr.read(in);
		      
		      XulDomContainer container = new HtmlXulLoader().loadXul(doc);
		      assertNotNull( "test document could not be read", container );
		      return container;

		    } catch(Exception e){
		      e.printStackTrace();
		    }
		    return null;
	}
	
	private void setupMenubar() {
	      XulDomContainer container = getStartDom();

	      List<XulComponent> menubars = container.getDocumentRoot().getElementsByTagName( "menubar" );
	      assertNotNull( "Menubar list is null", menubars );
	      assertEquals( "1 menubar expected", 1, menubars.size() );
	      assertTrue( "Menubar not found", menubars.get(0) instanceof XulMenubar );
	      menubar = (XulMenubar) menubars.get(0);
	      try {
		      xulLoader = new HtmlXulLoader();
	      } catch (Exception e) {
	    	  
	      }
	}
	
	public void testInvalidAnchor() {
    	setupMenubar();
	      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
	    String itemId = "item0";
	    MenuCustomization custom = new MenuCustomization( itemId, "bogus-anchor-id", "item 0", "", ItemType.MENU_ITEM, CustomizationType.INSERT_BEFORE );
	    customizations.add( custom );
	      
	    boolean result = MenuUtil.customizeMenu(menubar, customizations, xulLoader);
	    assertTrue( "customization should have failed", result == false );
	}
	
	public void testMenubarItemInsertBefore() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemId = "new-item";
		      MenuCustomization custom = new MenuCustomization( itemId, "menubar-item-2", "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.INSERT_BEFORE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );
		      
		      XulComponent validate = menubar.getElementByXPath( "menu[2]/menuitem[1]" );
		      assertNotNull( "Menu item not found where exected", validate );
		      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem );
		      assertEquals( "Customized item not where expected", itemId, validate.getId() );

		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Could not find item in HTML", html.indexOf( "<a id=\"new-item-menu\" href=\"command1\">item 0</a>" ) > 0 );
		      assertTrue( "Could not find item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'new-item-menu';" ) > 0 );
		      
	    	} catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}
	
	public void testMenubarItemInsertAfter() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemId = "new-item";
		      MenuCustomization custom = new MenuCustomization( itemId, "menubar-item-2", "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.INSERT_AFTER );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );
		      
		      XulComponent validate = menubar.getElementByXPath( "menu[3]/menuitem[1]" );
		      assertNotNull( "Menu item not found where exected", validate );
		      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem );
		      assertEquals( "Customized item not where expected", itemId, validate.getId() );

		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Could not find item in HTML", html.indexOf( "<a id=\"new-item-menu\" href=\"command1\">item 0</a>" ) > 0 );
		      assertTrue( "Could not find item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'new-item-menu';" ) > 0 );

		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}
	
	public void testMenubarItemDeleteFromFront() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemToDeleteId = "menubar-item-to-delete1";

		      XulComponent validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNotNull( "Item to delete not found", validate );

		      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );

		      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNull( "Item was not deleted", validate );

		      // first item should no longer be there
		      validate = menubar.getElementByXPath( "menu[1]/menuitem[1]" );
		      assertNotNull( "Menu item not found where exected", validate );
		      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem );
		      assertTrue( "Deleted item still exists", !validate.getId().equals( itemToDeleteId ) );

		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Should not have found item in HTML", html.indexOf( "<a id=\"menu1\"" ) == -1 );
		      assertTrue( "Should not have found item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'menu1';" ) == -1 );
		      
		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}

	public void testMenubarItemDeleteFromMiddle() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemToDeleteId = "menubar-item-to-delete2";

		      XulComponent validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNotNull( "Item to delete not found", validate );

		      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );

		      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNull( "Item was not deleted", validate );

		      // first item should no longer be there
		      validate = menubar.getElementByXPath( "menu[3]/menuitem[1]" );
		      assertNotNull( "Menu item not found where exected", validate );
		      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem );
		      assertTrue( "Deleted item still exists", !validate.getId().equals( itemToDeleteId ) );

		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Should not have found item in HTML", html.indexOf( "<a id=\"menu3\"" ) == -1 );
		      assertTrue( "Should not have found item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'menu3';" ) == -1 );

		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}

	public void testMenubarItemDeleteFromEnd() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemToDeleteId = "menubar-item-to-delete3";

		      XulComponent validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNotNull( "Item to delete not found", validate );

		      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );

		      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNull( "Item was not deleted", validate );

		      // first item should no longer be there
		      validate = menubar.getElementByXPath( "menu[7]" );
		      assertNull( "Deleted item still exists", validate );

		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Should not have found item in HTML", html.indexOf( "<a id=\"menu7\"" ) == -1 );
		      assertTrue( "Should not have found item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'menu7';" ) == -1 );

		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}
	
	public void testMenubarItemReplace() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemToReplaceId = "menubar-item-to-replace";
		      String newItemId = "new-menubar-item";

		      XulComponent validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToReplaceId+"']" );
		      assertNotNull( "Item to replace not found", validate );

		      MenuCustomization custom = new MenuCustomization( newItemId, itemToReplaceId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.REPLACE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );

		      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToReplaceId+"']" );
		      assertNull( "Item was not replaced", validate );

		      validate = menubar.getElementByXPath( "menu[4]/menuitem[@ID='"+newItemId+"']" );
		      assertNotNull( "Customized item not added", validate );
		      
		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Should not have found item in HTML", html.indexOf( "<a id=\"menu4\"" ) == -1 );
		      assertTrue( "Should not have found item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'menu4';" ) == -1 );
		      assertTrue( "Could not find item in HTML", html.indexOf( "<a id=\"new-menubar-item-menu\" href=\"command1\">item 0</a>" ) > 0 );
		      assertTrue( "Could not find item in script", html.indexOf( "topLevelMenuItems[topLevelMenuItems.length] = 'new-menubar-item-menu';" ) > 0 );

		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}

 	public void testSubmenuItemInsertBefore() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemId = "new-item";
		      MenuCustomization custom = new MenuCustomization( itemId, "submenu-item-1", "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.INSERT_BEFORE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );
		      
		      XulComponent validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[2]" );
		      assertNotNull( "Menu item not found where exected", validate );
		      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem );
		      assertEquals( "Customized item not where expected", itemId, validate.getId() );

		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"item 0\", \"command1\");" ) > 0 );

	    	} catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}

	public void testSubmenuItemInsertAfter() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemId = "new-item";
		      MenuCustomization custom = new MenuCustomization( itemId, "submenu-item-1", "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.INSERT_AFTER );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );
		      
		      XulComponent validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[3]" );
		      assertNotNull( "Menu item not found where exected", validate );
		      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem );
		      assertEquals( "Customized item not where expected", itemId, validate.getId() );

		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"item 0\", \"command1\");" ) > 0 );

		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}
	
	public void testSubmenuItemDeleteFromFront() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemToDeleteId = "submenu-item-to-delete1";

		      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNotNull( "Item to delete not found", validate );

		      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );

		      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNull( "Item was not deleted", validate );

		      // first item should no longer be there
		      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[1]" );
		      assertNotNull( "Menu item not found where exected", validate );
		      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem );
		      assertTrue( "Deleted item still exists", !validate.getId().equals( itemToDeleteId ) );
		      
		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Should not have found item in script", html.indexOf( "menu.addItem(\"item6-1\", \"\");" ) == -1 );
		      
		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}

	public void testSubmenuItemDeleteFromMiddle() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemToDeleteId = "submenu-item-to-delete2";

		      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNotNull( "Item to delete not found", validate );

		      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );

		      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNull( "Item was not deleted", validate );

		      // first item should no longer be there
		      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[3]" );
		      assertNotNull( "Menu item not found where exected", validate );
		      assertTrue( "Menu item not of right type", validate instanceof XulMenuitem );
		      assertTrue( "Deleted item still exists", !validate.getId().equals( itemToDeleteId ) );
		      
		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Should not have found item in script", html.indexOf( "menu.addItem(\"item6-3\", \"\");" ) == -1 );

	    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}


	public void testSubmenuItemDeleteFromEnd() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemToDeleteId = "submenu-item-to-delete3";

		      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNotNull( "Item to delete not found", validate );

		      MenuCustomization custom = new MenuCustomization( "", itemToDeleteId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.DELETE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );

		      validate = menubar.getElementByXPath( "menu/menuitem[@ID='"+itemToDeleteId+"']" );
		      assertNull( "Item was not deleted", validate );

		      // first item should no longer be there
		      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[6]" );
		      assertNull( "Deleted item still exists", validate );
		      
		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Should not have found item in script", html.indexOf( "menu.addItem(\"item6-6\", \"\");" ) == -1 );
		      
		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}

	public void testSubmenuItemReplace() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String itemToReplaceId = "submenu-item-to-replace";
		      String newItemId = "new-submenu-item";

		      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToReplaceId+"']" );
		      assertNotNull( "Item to replace not found", validate );

		      MenuCustomization custom = new MenuCustomization( newItemId, itemToReplaceId, "item 0", "command1", ItemType.MENU_ITEM, CustomizationType.REPLACE );
		      customizations.add( custom );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
//		      System.out.println( element.asXML() );

		      validate = menubar.getElementByXPath( "//menuitem[@ID='"+itemToReplaceId+"']" );
		      assertNull( "Item was not replaced", validate );

		      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menuitem[@ID='"+newItemId+"']" );
		      assertNotNull( "Customized item not added", validate );

		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
//		      System.out.println( html );
		      assertTrue( "Should not have found item in script", html.indexOf( "menu.addItem(\"item6-4\", \"\");" ) == -1 );
		      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"item 0\", \"command1\");" ) > 0 );


		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}
	
	public void testNewSubmenu() {
	    try{
	    	setupMenubar();
		      List<IMenuCustomization> customizations = new ArrayList<IMenuCustomization>();
		      String menuId = "new-menu";
		      String newItemId1 = "new-item1";
		      String newItemId2 = "new-item2";
		      String newItemId3 = "new-item3";

		      customizations.add( new MenuCustomization( menuId, "menubar-item-2", "new menu", "", ItemType.SUBMENU, CustomizationType.INSERT_BEFORE ) );
		      customizations.add( new MenuCustomization( newItemId1, menuId, "new item 1", "command1", ItemType.MENU_ITEM, CustomizationType.FIRST_CHILD ) );
		      customizations.add( new MenuCustomization( newItemId2, newItemId1, "new item 2", "command2", ItemType.MENU_ITEM, CustomizationType.INSERT_AFTER ) );
		      customizations.add( new MenuCustomization( newItemId3, menuId, "new item 3", "command3", ItemType.MENU_ITEM, CustomizationType.LAST_CHILD ) );
		      
		      MenuUtil.customizeMenu(menubar, customizations, xulLoader);
		      Element element = (Element) menubar.getElementObject();
		      System.out.println( element.asXML() );

		      XulComponent validate = menubar.getElementByXPath( "//menuitem[@ID='"+newItemId1+"']" );
		      assertNotNull( "Item was not added", validate );

		      validate = menubar.getElementByXPath( "//menuitem[@ID='"+newItemId2+"']" );
		      assertNotNull( "Item was not added", validate );

		      validate = menubar.getElementByXPath( "//menuitem[@ID='"+newItemId3+"']" );
		      assertNotNull( "Item was not added", validate );

		      validate = menubar.getElementByXPath( "menu[2]/menupopup[1]/menuitem[1]" );
		      assertNotNull( "Customized item not added", validate );
		      assertTrue( "Customized item not added", newItemId1.equals( validate.getId() ) );
		      
		      validate = menubar.getElementByXPath( "menu[2]/menupopup[1]/menuitem[2]" );
		      assertNotNull( "Customized item not added", validate );
		      assertTrue( "Customized item not added", newItemId2.equals( validate.getId() ) );
		      
		      validate = menubar.getElementByXPath( "menu[2]/menupopup[1]/menuitem[3]" );
		      assertNotNull( "Customized item not added", validate );
		      assertTrue( "Customized item not added", newItemId3.equals( validate.getId() ) );
		      		      
		      StringBuilder sb = new StringBuilder();
		      ((IHtmlElement) menubar ).getHtml(sb);
		      String html = sb.toString();
		      // we should see an <a> tag for the menubar item
		      System.out.println( html );
		      assertTrue( "Could not find item in HTML", html.indexOf( "<a id=\"new-menu-menu\" href=\"#\">new menu</a>" ) > 0 );
		      assertTrue( "Could not find item in script", html.indexOf( "var menu = ms.addMenu(document.getElementById(\"new-menu-menu\"));" ) > 0 );
		      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"new item 1\", \"command1\");" ) > 0 );
		      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"new item 2\", \"command2\");" ) > 0 );
		      assertTrue( "Could not find item in script", html.indexOf( "menu.addItem(\"new item 3\", \"command3\");" ) > 0 );

		    } catch(Exception e){
		      System.out.println(e.getMessage());
		      e.printStackTrace(System.out);
		    }

	}
	
}
