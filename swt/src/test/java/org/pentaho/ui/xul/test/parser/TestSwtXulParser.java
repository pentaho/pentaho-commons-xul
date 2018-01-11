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

/**
 * 
 */

package org.pentaho.ui.xul.test.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.Collections;

import org.dom4j.io.SAXReader;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.impl.XulParser;
import org.pentaho.ui.xul.impl.XulWindowContainer;

/**
 * @author OEM
 * 
 */
public class TestSwtXulParser {
  org.dom4j.Document testDoc;

  XulDomContainer container;

  private XulParser parser;

  @Before
  public void setUp() throws Exception {
    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    DocumentFactory.registerDOMClass( DocumentDom4J.class );
    DocumentFactory.registerElementClass( ElementDom4J.class );
    InputStream in = getClass().getResourceAsStream( "/documents/allTags.xul" );
    SAXReader rdr = new SAXReader();
    testDoc = rdr.read( in );

    try {
      parser = new XulParser();
    } catch ( Exception e ) {
      throw new XulException( "Error getting XulParser Instance, probably a DOM Factory problem: " + e.getMessage(), e );
    }

    // attach Renderers
    parser.registerHandler( "WINDOW", "org.pentaho.ui.xul.swt.tags.SwtWindow" );
    parser.registerHandler( "DIALOG", "org.pentaho.ui.xul.swt.tags.SwtDialog" );
    parser.registerHandler( "BUTTON", "org.pentaho.ui.xul.swt.tags.SwtButton" );
    parser.registerHandler( "BOX", "org.pentaho.ui.xul.swt.tags.SwtBox" );
    parser.registerHandler( "VBOX", "org.pentaho.ui.xul.swt.tags.SwtVbox" );
    parser.registerHandler( "HBOX", "org.pentaho.ui.xul.swt.tags.SwtHbox" );
    parser.registerHandler( "LABEL", "org.pentaho.ui.xul.swt.tags.SwtLabel" );
    parser.registerHandler( "TEXTBOX", "org.pentaho.ui.xul.swt.tags.SwtTextbox" );
    parser.registerHandler( "GROUPBOX", "org.pentaho.ui.xul.swt.tags.SwtGroupbox" );
    parser.registerHandler( "CAPTION", "org.pentaho.ui.xul.swt.tags.SwtCaption" );
    parser.registerHandler( "LISTBOX", "org.pentaho.ui.xul.swt.tags.SwtListbox" );
    parser.registerHandler( "LISTITEM", "org.pentaho.ui.xul.swt.tags.SwtListitem" );
    parser.registerHandler( "SCRIPT", "org.pentaho.ui.xul.swt.tags.SwtScript" );
    parser.registerHandler( "CHECKBOX", "org.pentaho.ui.xul.swt.tags.SwtCheckbox" );
    parser.registerHandler( "MESSAGEBOX", "org.pentaho.ui.xul.swt.tags.SwtMessageBox" );
    parser.registerHandler( "ERRORMESSAGEBOX", "org.pentaho.ui.xul.swt.tags.SwtErrorMessageBox" );
    parser.registerHandler( "DECK", "org.pentaho.ui.xul.swt.tags.SwtDeck" );
    parser.registerHandler( "TREE", "org.pentaho.ui.xul.swt.tags.SwtTree" );
    parser.registerHandler( "TREECOLS", "org.pentaho.ui.xul.swt.tags.SwtTreeCols" );
    parser.registerHandler( "TREECOL", "org.pentaho.ui.xul.swt.tags.SwtTreeCol" );
    parser.registerHandler( "TREECHILDREN", "org.pentaho.ui.xul.swt.tags.SwtTreeChildren" );
    parser.registerHandler( "TREEITEM", "org.pentaho.ui.xul.swt.tags.SwtTreeItem" );
    parser.registerHandler( "TREEROW", "org.pentaho.ui.xul.swt.tags.SwtTreeRow" );
    parser.registerHandler( "TREECELL", "org.pentaho.ui.xul.swt.tags.SwtTreeCell" );
    parser.registerHandler( "PROGRESSMETER", "org.pentaho.ui.xul.swt.tags.SwtProgressmeter" );
    parser.registerHandler( "SPACER", "org.pentaho.ui.xul.swt.tags.SwtSpacer" );

    parser.registerHandler( "TABBOX", "org.pentaho.ui.xul.swt.tags.SwtTabbox" );
    parser.registerHandler( "TABS", "org.pentaho.ui.xul.swt.tags.SwtTabs" );
    parser.registerHandler( "TAB", "org.pentaho.ui.xul.swt.tags.SwtTab" );
    parser.registerHandler( "TABPANELS", "org.pentaho.ui.xul.swt.tags.SwtTabpanels" );
    parser.registerHandler( "TABPANEL", "org.pentaho.ui.xul.swt.tags.SwtTabpanel" );
    parser.registerHandler( "MENULIST", "org.pentaho.ui.xul.swt.tags.SwtMenuList" );
    parser.registerHandler( "MENUPOPUP", "org.pentaho.ui.xul.swt.tags.SwtMenupopup" );
    parser.registerHandler( "MENUITEM", "org.pentaho.ui.xul.swt.tags.SwtMenuitem" );
    parser.registerHandler( "MENUSEPARATOR", "org.pentaho.ui.xul.swt.tags.SwtMenuseparator" );
    parser.registerHandler( "MENU", "org.pentaho.ui.xul.swt.tags.SwtMenu" );
    parser.registerHandler( "MENUBAR", "org.pentaho.ui.xul.swt.tags.SwtMenubar" );
    parser.registerHandler( "RADIOGROUP", "org.pentaho.ui.xul.swt.tags.SwtRadioGroup" );
    parser.registerHandler( "RADIO", "org.pentaho.ui.xul.swt.tags.SwtRadio" );
    parser.registerHandler( "IMAGE", "org.pentaho.ui.xul.swt.tags.SwtImage" );
    parser.registerHandler( "FILEDIALOG", "org.pentaho.ui.xul.swt.tags.SwtFileDialog" );
    parser.registerHandler( "SPLITTER", "org.pentaho.ui.xul.swt.tags.SwtSplitter" );
    parser.registerHandler( "OVERLAY", "org.pentaho.ui.xul.swt.tags.SwtOverlay" );

    parser.registerHandler( "TOOLBAR", "org.pentaho.ui.xul.swt.tags.SwtToolbar" );
    parser.registerHandler( "TOOLBARSEPARATOR", "org.pentaho.ui.xul.swt.tags.SwtToolbarseparator" );
    parser.registerHandler( "TOOLBARBUTTON", "org.pentaho.ui.xul.swt.tags.SwtToolbarbutton" );
    parser.registerHandler( "TOOLBARITEM", "org.pentaho.ui.xul.swt.tags.SwtToolbaritem" );
    parser.registerHandler( "TOOLBARSPACER", "org.pentaho.ui.xul.swt.tags.SwtToolbarspacer" );

    parser.registerHandler( "PROMPTBOX", "org.pentaho.ui.xul.swt.tags.SwtPromptBox" );
    parser.registerHandler( "CONFIRMBOX", "org.pentaho.ui.xul.swt.tags.SwtConfirmBox" );
    parser.registerHandler( "EDITPANEL", "org.pentaho.ui.xul.swt.tags.SwtEditpanel" );
    parser.registerHandler( "BROWSER", "org.pentaho.ui.xul.swt.tags.SwtBrowser" );

    parser.registerHandler( "GRID", "org.pentaho.ui.xul.swt.tags.SwtGrid" );
    parser.registerHandler( "COLUMNS", "org.pentaho.ui.xul.swt.tags.SwtColumns" );
    parser.registerHandler( "COLUMN", "org.pentaho.ui.xul.swt.tags.SwtColumn" );
    parser.registerHandler( "ROWS", "org.pentaho.ui.xul.swt.tags.SwtRows" );
    parser.registerHandler( "ROW", "org.pentaho.ui.xul.swt.tags.SwtRow" );
    parser.registerHandler( "SCALE", "org.pentaho.ui.xul.swt.tags.SwtScale" );

    parser.registerHandler( "WAITBOX", "org.pentaho.ui.xul.swt.tags.SwtWaitBox" );

    parser.setClassLoaders( Collections.singletonList( getClass().getClassLoader() ) );

    container = new XulWindowContainer();
    parser.setContainer( container );
    parser.parseDocument( testDoc.getRootElement() );
  }

  @Test
  public void testRootElement() throws Exception {
    Document root = container.getDocumentRoot();
    assertNotNull( root );
  }

  @Test
  public void testRootIsWindow() throws Exception {
    Document root = container.getDocumentRoot();
    assertTrue( root.getRootElement() instanceof XulWindow );
  }

  @Test
  public void testElementsByXPath() throws Exception {
    Document root = container.getDocumentRoot();
    assertTrue( root.getElementByXPath( "/window" ) != null && root.getElementByXPath( "/window/button" ) != null
        && root.getElementByXPath( "/window/vbox" ) != null && root.getElementByXPath( "/window/vbox/button" ) != null
        && root.getElementByXPath( "/window/label" ) != null && root.getElementByXPath( "/window/hbox" ) != null
        && root.getElementByXPath( "/window/hbox/label" ) != null
        && root.getElementByXPath( "/window/textbox" ) != null && root.getElementByXPath( "/window/script" ) != null
        && root.getElementByXPath( "/window/checkbox" ) != null && root.getElementByXPath( "/window/groupbox" ) != null
        && root.getElementByXPath( "/window/groupbox/checkbox" ) != null
        && root.getElementByXPath( "/window/caption" ) != null && root.getElementByXPath( "/window/listbox" ) != null
        && root.getElementByXPath( "/window/listbox/listitem" ) != null );
  }

}
