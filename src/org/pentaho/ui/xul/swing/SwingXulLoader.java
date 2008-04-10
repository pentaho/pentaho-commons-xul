/**
 * 
 */
package org.pentaho.ui.xul.swing;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.impl.XulFragmentContainer;
import org.pentaho.ui.xul.impl.XulParser;
import org.pentaho.ui.xul.impl.XulWindowContainer;

/**
 * @author nbaker
 *
 */
public class SwingXulLoader implements XulLoader {

  private XulParser parser;
  public SwingXulLoader() throws XulException{

    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);
  
    
    try{
      parser = new XulParser();
    } catch(Exception e){
      throw new XulException("Error getting XulParser Instance, probably a DOM Factory problem: "+e.getMessage(), e);
    }
     

    //attach Renderers
    parser.registerHandler("WINDOW", "org.pentaho.ui.xul.swing.tags.SwingWindow");
    parser.registerHandler("BUTTON", "org.pentaho.ui.xul.swing.tags.SwingButton");
    parser.registerHandler("VBOX", "org.pentaho.ui.xul.swing.tags.SwingVbox");
    parser.registerHandler("HBOX", "org.pentaho.ui.xul.swing.tags.SwingHbox");
    parser.registerHandler("LABEL", "org.pentaho.ui.xul.swing.tags.SwingLabel");
    parser.registerHandler("TEXTBOX", "org.pentaho.ui.xul.swing.tags.SwingTextbox");
    parser.registerHandler("SCRIPT", "org.pentaho.ui.xul.swing.tags.SwingScript");
    parser.registerHandler("SPACER", "org.pentaho.ui.xul.swing.tags.SwingSpacer");
    parser.registerHandler("CHECKBOX", "org.pentaho.ui.xul.swing.tags.SwingCheckbox");
    parser.registerHandler("GROUPBOX", "org.pentaho.ui.xul.swing.tags.SwingGroupbox");
    parser.registerHandler("CAPTION", "org.pentaho.ui.xul.swing.tags.SwingCaption");
    parser.registerHandler("LISTBOX", "org.pentaho.ui.xul.swing.tags.SwingListbox");
    parser.registerHandler("LISTITEM", "org.pentaho.ui.xul.swing.tags.SwingListitem");
    parser.registerHandler("MESSAGEBOX", "org.pentaho.ui.xul.swing.tags.SwingMessageBox");
    parser.registerHandler("DECK", "org.pentaho.ui.xul.swing.tags.SwingDeck");
    parser.registerHandler("MENUBAR", "org.pentaho.ui.xul.swing.tags.SwingMenubar");
    parser.registerHandler("MENU", "org.pentaho.ui.xul.swing.tags.SwingMenu");
    parser.registerHandler("MENUPOPUP", "org.pentaho.ui.xul.swing.tags.SwingMenupopup");
    parser.registerHandler("MENUITEM", "org.pentaho.ui.xul.swing.tags.SwingMenuitem");
    parser.registerHandler("MENUSEPARATOR", "org.pentaho.ui.xul.swing.tags.SwingMenuseparator");
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException{

    XulDomContainer container = new XulWindowContainer(this);
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement());
   
    return container;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment(Document xulDocument) throws IllegalArgumentException, XulException {
    XulDomContainer container = new XulFragmentContainer(this);
    parser.reset();
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement());
    
    return container;
  }
}
