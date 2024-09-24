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

package org.pentaho.ui.xul.html;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.impl.AbstractXulLoader;

public class HtmlXulLoader extends AbstractXulLoader {

	 XulComponent defaultParent = null;
	 
	 public HtmlXulLoader() throws XulException{
		    super();
		    
		    //attach Renderers
		    parser.registerHandler("WINDOW", "org.pentaho.ui.xul.html.tags.HtmlWindow"); //$NON-NLS-1$ //$NON-NLS-2$
		    /* TODO these are all 
		    parser.registerHandler("DIALOG", "org.pentaho.ui.xul.swt.tags.SwtDialog");
		    parser.registerHandler("BUTTON", "org.pentaho.ui.xul.swt.tags.SwtButton");
		    parser.registerHandler("BOX", "org.pentaho.ui.xul.swt.tags.SwtBox");
		    parser.registerHandler("VBOX", "org.pentaho.ui.xul.swt.tags.SwtVbox");
		    parser.registerHandler("HBOX", "org.pentaho.ui.xul.swt.tags.SwtHbox");
		    parser.registerHandler("LABEL", "org.pentaho.ui.xul.swt.tags.SwtLabel");
		    parser.registerHandler("TEXTBOX", "org.pentaho.ui.xul.swt.tags.SwtTextbox");
		    parser.registerHandler("GROUPBOX", "org.pentaho.ui.xul.swt.tags.SwtGroupbox");
		    parser.registerHandler("CAPTION", "org.pentaho.ui.xul.swt.tags.SwtCaption");
		    parser.registerHandler("LISTBOX", "org.pentaho.ui.xul.swt.tags.SwtListbox");
		    parser.registerHandler("LISTITEM", "org.pentaho.ui.xul.swt.tags.SwtListitem");
		    parser.registerHandler("SCRIPT", "org.pentaho.ui.xul.swt.tags.SwtScript");
		    parser.registerHandler("CHECKBOX", "org.pentaho.ui.xul.swt.tags.SwtCheckbox");
		    parser.registerHandler("MESSAGEBOX", "org.pentaho.ui.xul.swt.tags.SwtMessageBox");
		    parser.registerHandler("ERRORMESSAGEBOX", "org.pentaho.ui.xul.swt.tags.SwtErrorMessageBox");
		    parser.registerHandler("DECK", "org.pentaho.ui.xul.swt.tags.SwtDeck");
		    parser.registerHandler("TREE", "org.pentaho.ui.xul.swt.tags.SwtTree");
		    parser.registerHandler("TREECOLS", "org.pentaho.ui.xul.swt.tags.SwtTreeCols");
		    parser.registerHandler("TREECOL", "org.pentaho.ui.xul.swt.tags.SwtTreeCol");
		    parser.registerHandler("TREECHILDREN", "org.pentaho.ui.xul.swt.tags.SwtTreeChildren");
		    parser.registerHandler("TREEITEM", "org.pentaho.ui.xul.swt.tags.SwtTreeItem");
		    parser.registerHandler("TREEROW", "org.pentaho.ui.xul.swt.tags.SwtTreeRow");
		    parser.registerHandler("TREECELL", "org.pentaho.ui.xul.swt.tags.SwtTreeCell");
		    parser.registerHandler("PROGRESSMETER", "org.pentaho.ui.xul.swt.tags.SwtProgressmeter");
		    parser.registerHandler("SPACER", "org.pentaho.ui.xul.swt.tags.SwtSpacer");
		    

		    parser.registerHandler("TABBOX", "org.pentaho.ui.xul.swt.tags.SwtTabbox");
		    parser.registerHandler("TABS", "org.pentaho.ui.xul.swt.tags.SwtTabs");
		    parser.registerHandler("TAB", "org.pentaho.ui.xul.swt.tags.SwtTab");
		    parser.registerHandler("TABPANELS", "org.pentaho.ui.xul.swt.tags.SwtTabpanels");
		    parser.registerHandler("TABPANEL", "org.pentaho.ui.xul.swt.tags.SwtTabpanel");
		    */
		    parser.registerHandler("MENU", "org.pentaho.ui.xul.html.tags.transmenu.HtmlMenu"); //$NON-NLS-1$ //$NON-NLS-2$
		    parser.registerHandler("MENUPOPUP", "org.pentaho.ui.xul.html.tags.transmenu.HtmlMenupopup");  //$NON-NLS-1$//$NON-NLS-2$
		    parser.registerHandler("MENUITEM", "org.pentaho.ui.xul.html.tags.transmenu.HtmlMenuitem");  //$NON-NLS-1$//$NON-NLS-2$
		    parser.registerHandler("MENUBAR", "org.pentaho.ui.xul.html.tags.transmenu.HtmlMenubar");  //$NON-NLS-1$//$NON-NLS-2$
		    		    
		  }

		  public XulLoader getNewInstance() throws XulException {
		    return new HtmlXulLoader();
		  }

		  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException {
		    return loadXul(xulDocument, outerContext);
		  }

		  public XulDomContainer loadXul(Document xulDocument, Object context) throws IllegalArgumentException, XulException {
		    
		    setOuterContext(context);
		    XulDomContainer domC = super.loadXul(xulDocument);
		    
		    return domC;

		  }
		  
		  @Override
		  public XulComponent createElement(String elementName) throws XulException{
		    return parser.getElement(elementName, defaultParent);
		  }

      public XulDomContainer loadXul(String resource, Object bundle) throws XulException {
        throw new UnsupportedOperationException();
      }

      public XulDomContainer loadXulFragment(String resource, Object bundle) throws XulException {
        throw new UnsupportedOperationException();
          
      }

		  

}
