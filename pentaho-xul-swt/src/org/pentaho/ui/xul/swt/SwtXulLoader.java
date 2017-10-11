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

package org.pentaho.ui.xul.swt;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.impl.AbstractXulLoader;
import org.pentaho.ui.xul.swt.tags.SwtDialog;
import org.pentaho.ui.xul.swt.tags.SwtWindow;

/**
 * @author NBaker
 * 
 */
public class SwtXulLoader extends AbstractXulLoader {

  XulComponent defaultParent = null;

  public SwtXulLoader() throws XulException {
    super();

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
    parser.registerHandler( "SEPARATOR", "org.pentaho.ui.xul.swt.tags.SwtSeparator" );

    parser.registerHandler( "TABBOX", "org.pentaho.ui.xul.swt.tags.SwtTabbox" );
    parser.registerHandler( "TABS", "org.pentaho.ui.xul.swt.tags.SwtTabs" );
    parser.registerHandler( "TAB", "org.pentaho.ui.xul.swt.tags.SwtTab" );
    parser.registerHandler( "TABPANELS", "org.pentaho.ui.xul.swt.tags.SwtTabpanels" );
    parser.registerHandler( "TABPANEL", "org.pentaho.ui.xul.swt.tags.SwtTabpanel" );
    parser.registerHandler( "MENULIST", "org.pentaho.ui.xul.jface.tags.JfaceMenuList" );
    parser.registerHandler( "MENUPOPUP", "org.pentaho.ui.xul.jface.tags.JfaceMenupopup" );
    parser.registerHandler( "MENUITEM", "org.pentaho.ui.xul.jface.tags.JfaceMenuitem" );
    parser.registerHandler( "MENUSEPARATOR", "org.pentaho.ui.xul.jface.tags.JfaceMenuseparator" );
    parser.registerHandler( "MENU", "org.pentaho.ui.xul.jface.tags.JfaceMenu" );
    parser.registerHandler( "MENUBAR", "org.pentaho.ui.xul.jface.tags.JfaceMenubar" );
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

    // parser.registerHandler("ACCORDION", "org.pentaho.ui.xul.swt.tags.SwtAccordion");
    // parser.registerHandler("ACCORDIONPANEL", "org.pentaho.ui.xul.swt.tags.SwtAccordionPanel");

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
  }

  public XulLoader getNewInstance() throws XulException {
    return new SwtXulLoader();
  }

  @Override
  public XulDomContainer loadXul( Object xulDocument ) throws IllegalArgumentException, XulException {
    return loadXul( (Document) xulDocument, outerContext );
  }

  public XulDomContainer loadXul( Document xulDocument, Object context ) throws IllegalArgumentException, XulException {

    setOuterContext( context );
    XulDomContainer domC = super.loadXul( xulDocument );

    // SWT has no notion of an "onload" event, so we must simulate it...
    // This needs to be replaced by an agnostic listener pattern.

    XulComponent maybeWindow = domC.getDocumentRoot().getRootElement();
    if ( maybeWindow instanceof SwtWindow ) {
      SwtWindow window = (SwtWindow) maybeWindow;
      window.notifyListeners( XulRoot.EVENT_ON_LOAD );
      defaultParent = window;
    }

    XulComponent maybeDialog = domC.getDocumentRoot().getRootElement();
    if ( maybeWindow instanceof SwtDialog ) {
      SwtDialog dialog = (SwtDialog) maybeDialog;
      dialog.notifyListeners( XulRoot.EVENT_ON_LOAD );
      defaultParent = dialog;
    }

    return domC;

  }

  @Override
  public XulComponent createElement( String elementName ) throws XulException {
    return parser.getElement( elementName, defaultParent );
  }

}
