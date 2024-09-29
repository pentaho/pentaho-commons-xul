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
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.html.HtmlXulLoader;
import org.pentaho.ui.xul.html.tags.transmenu.HtmlMenu;
import org.pentaho.ui.xul.html.tags.transmenu.HtmlMenubar;
import org.pentaho.ui.xul.html.tags.transmenu.HtmlMenuitem;
import org.pentaho.ui.xul.html.tags.transmenu.HtmlMenupopup;
import org.pentaho.ui.xul.test.html.HtmlHarness;
import org.pentaho.ui.xul.util.Orient;

import junit.framework.TestCase;

public class MenuTest extends TestCase {

	private XulMenubar menubar; // we manipulate this menubar in each test
	
	private XulDomContainer getMenubar() {
		return getContainer( "documents/htmlmenutest.xul" ); //$NON-NLS-1$
	}	
	
	private XulDomContainer getContainer( String filePath ) {
	    try{
		      InputStream in = HtmlHarness.class.getClassLoader().getResourceAsStream( filePath );

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
	      XulDomContainer container = getMenubar();

	      List<XulComponent> menubars = container.getDocumentRoot().getElementsByTagName( "menubar" ); //$NON-NLS-1$
	      assertNotNull( "Menubar list is null", menubars ); //$NON-NLS-1$
	      assertEquals( "1 menubar expected", 1, menubars.size() ); //$NON-NLS-1$
	      assertTrue( "Menubar not found", menubars.get(0) instanceof XulMenubar ); //$NON-NLS-1$
	      menubar = (XulMenubar) menubars.get(0);
	}	
	
	public void testMenuItem() {
    	setupMenubar();

	      XulComponent validate = menubar.getElementByXPath( "menu[1]/menuitem[1]" ); //$NON-NLS-1$
	      assertNotNull( "Item not found", validate ); //$NON-NLS-1$
	      assertTrue( "Item not a HtmlMenuitem", validate instanceof HtmlMenuitem ); //$NON-NLS-1$
	      
	      HtmlMenuitem item = (HtmlMenuitem) validate;
	      
	      // check unsupported features
	      item.setAcceltext( "test" ); //$NON-NLS-1$
	      assertNull( "setAcceltext is not supported", item.getAcceltext() ); //$NON-NLS-1$

	      item.setAccesskey( "test" ); //$NON-NLS-1$
	      assertNull( "setAccesskey is not supported", item.getAccesskey() ); //$NON-NLS-1$

	      item.setImage( "test" ); //$NON-NLS-1$
	      assertNull( "setImage is not supported", item.getImage() ); //$NON-NLS-1$

	      item.setDisabled( true );
	      assertFalse( "setDisabled is not supported", item.isDisabled() ); //$NON-NLS-1$

	      item.setSelected( true );
	      assertFalse( "setDisabled is not supported", item.isSelected() ); //$NON-NLS-1$
	      
	      // these do nothing but should not fail
	      item.addPropertyChangeListener( null );
	      item.removePropertyChangeListener( null );
	      item.getHtml( null );
	      
	      item.setLabel( "test menuitem label" ); //$NON-NLS-1$
	      assertTrue( "Set label failed", item.getLabel().equals( "test menuitem label" ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testMenu() {
    	setupMenubar();

	      XulComponent validate = menubar.getElementByXPath( "menu[1]" ); //$NON-NLS-1$
	      assertNotNull( "Item not found", validate ); //$NON-NLS-1$
	      assertTrue( "Item not a HtmlMenu", validate instanceof HtmlMenu ); //$NON-NLS-1$
	      
	      HtmlMenu item = (HtmlMenu) validate;
	      
	      // check unsupported features
	      item.setAcceltext( "test" ); //$NON-NLS-1$
	      assertNull( "setAcceltext is not supported", item.getAcceltext() ); //$NON-NLS-1$

	      item.setAccesskey( "test" ); //$NON-NLS-1$
	      assertNull( "setAccesskey is not supported", item.getAccesskey() ); //$NON-NLS-1$

	      item.setDisabled( true );
	      assertFalse( "setDisabled is not supported", item.isDisabled() ); //$NON-NLS-1$

	      // these do nothing but should not fail
	      item.addPropertyChangeListener( null );
	      item.removePropertyChangeListener( null );
	      
	      item.setLabel( "test menu label" ); //$NON-NLS-1$
	      assertTrue( "Set label failed", item.getLabel().equals( "test menu label" ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

  public void testMenuPopup() {
    setupMenubar();

      XulComponent validate = menubar.getElementByXPath( "menu[6]/menupopup[1]" ); //$NON-NLS-1$
      assertNotNull( "Item not found", validate ); //$NON-NLS-1$
      assertTrue( "Item not a HtmlMenupopup", validate instanceof HtmlMenupopup ); //$NON-NLS-1$
      
      HtmlMenupopup item = (HtmlMenupopup) validate;
      
      item.setDisabled( true );
      assertFalse( "setDisabled is not supported", item.isDisabled() ); //$NON-NLS-1$

      // these do nothing but should not fail
      item.addPropertyChangeListener( null );
      item.removePropertyChangeListener( null );
      item.getHtml( null );
      
}

  public void testSubSubMenu() {
    setupMenubar();

      XulComponent validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menu[1]" ); //$NON-NLS-1$
      assertNotNull( "Item not found", validate ); //$NON-NLS-1$
      assertTrue( "Item not a HtmlMenupopup", validate instanceof HtmlMenu ); //$NON-NLS-1$
      
      HtmlMenu menu = (HtmlMenu) validate;
      assertEquals( "Menu label is wrong", "item6-7", menu.getLabel() );  //$NON-NLS-1$//$NON-NLS-2$
      assertEquals( "Menu id is wrong", "submenu-menu-1", menu.getId() );  //$NON-NLS-1$//$NON-NLS-2$
      
      validate = menubar.getElementByXPath( "menu[6]/menupopup[1]/menu[1]/menupopup[1]/menuitem[1]" ); //$NON-NLS-1$
      assertNotNull( "Item not found", validate ); //$NON-NLS-1$
      assertTrue( "Item not a HtmlMenuitem", validate instanceof HtmlMenuitem ); //$NON-NLS-1$
      
      HtmlMenuitem item = (HtmlMenuitem) validate;
      assertEquals( "Menu item label is wrong", "item6-7-1", item.getLabel() );  //$NON-NLS-1$//$NON-NLS-2$
      assertEquals( "Menu item id is wrong", "3rd-level-item", item.getId() );  //$NON-NLS-1$//$NON-NLS-2$
            
      StringBuilder sb = new StringBuilder();
      ((HtmlMenubar) menubar).getHtml(sb);
      String html = sb.toString();
      System.out.println( sb.toString() );
      
      assertTrue( "Menu label not in HTML", html.indexOf( "item1-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command1-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item2-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command2-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item3-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command3-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item4-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command4-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item5-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command5-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "menu6" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item6-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command6-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item6-2" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command6-2" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item6-3" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command6-3" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item6-4" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command6-4" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item6-5" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command6-5" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item6-6" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command6-6" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item6-7" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item6-7-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command6-7-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$

      assertTrue( "Menu label not in HTML", html.indexOf( "item7-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      assertTrue( "Menu command not in HTML", html.indexOf( "command7-1" ) != -1 ); //$NON-NLS-1$ //$NON-NLS-2$
      
      sb = new StringBuilder();
      ((HtmlMenubar) menubar).getScript(sb);
      String script = sb.toString();
      
      assertTrue( "Script is not in HTML", html.indexOf( script ) != -1 ); //$NON-NLS-1$
      
}

	public void testMenubar() {
    	setupMenubar();

	      assertNotNull( "Menubar is null", menubar ); //$NON-NLS-1$
	      assertTrue( "Item not a HtmlMenuitem", menubar instanceof HtmlMenubar ); //$NON-NLS-1$
	      
	      HtmlMenubar bar = (HtmlMenubar) menubar;
	      
	      // check unsupported features

	      bar.setDisabled( true );
	      assertFalse( "setDisabled is not supported", bar.isDisabled() ); //$NON-NLS-1$
	      
	      // these do nothing but should not fail
	      bar.addPropertyChangeListener( null );
	      bar.removePropertyChangeListener( null );
	      
	      assertEquals( "Default orientation should be horizontal", "HORIZONTAL", bar.getOrient() ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertEquals( "Default orientation should be horizontal", Orient.HORIZONTAL, bar.getOrientation() ); //$NON-NLS-1$ 
	      
	      bar.setOrient( Orient.VERTICAL.toString() );
	      assertEquals( "Default orientation should be horizontal", "VERTICAL", bar.getOrient() ); //$NON-NLS-1$ //$NON-NLS-2$
	      assertEquals( "Default orientation should be horizontal", Orient.VERTICAL, bar.getOrientation() ); //$NON-NLS-1$ 
	      
	}


}
