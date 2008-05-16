/**
 * 
 */
package org.pentaho.ui.xul.swt;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.impl.AbstractXulLoader;
import org.pentaho.ui.xul.impl.XulFragmentContainer;
import org.pentaho.ui.xul.impl.XulParser;
import org.pentaho.ui.xul.impl.XulWindowContainer;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtWindow;
import org.pentaho.ui.xul.util.ResourceBundleTranslator;

/**
 * @author NBaker
 *
 */
public class SwtXulLoader extends AbstractXulLoader{

  public SwtXulLoader() throws XulException{
    super();
    
    //attach Renderers
    parser.registerHandler("WINDOW", "org.pentaho.ui.xul.swt.tags.SwtWindow");
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
    
  }

  public XulLoader getNewInstance() throws XulException {
    return new SwtXulLoader();
  }
  

}