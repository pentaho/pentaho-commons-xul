/**
 * 
 */
package org.pentaho.ui.xul.swt;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulFragmentContainer;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.XulParser;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.swt.tags.SwtWindow;

/**
 * @author OEM
 *
 */
public class SwtXulLoader implements XulLoader {

  private XulParser parser;
  public SwtXulLoader() throws XulException{

    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);
    

    try{
      parser = new XulParser();
    } catch(Exception e){
      throw new XulException("Error getting XulParser Instance, probably a DOM Factory problem: "+e.getMessage(), e);
    }
    
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
    parser.registerHandler("DECK", "org.pentaho.ui.xul.swt.tags.SwtDeck");

    
     
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException{

    XulWindowContainer container = new XulWindowContainer();
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement());
    
    // SWT has no notion of an "onload" event, so we must simulate it...
    
    XulElement maybeWindow = container.getDocumentRoot().getXulElement();
    if ( maybeWindow instanceof SwtWindow){
      SwtWindow window = (SwtWindow) maybeWindow;
      window.notifyListeners(XulWindow.EVENT_ON_LOAD);
    }

    return container;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment(Document xulDocument) throws IllegalArgumentException, XulException {
    XulFragmentContainer container = new XulFragmentContainer();
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement());
    
    return container;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment(Document xulDocument, XulContainer parent) throws IllegalArgumentException, XulException {
    XulFragmentContainer container = new XulFragmentContainer();
    parser.setContainer(container);
    XulElement element = parser.parse(xulDocument.getRootElement(), parent);
    parser.getDocumentRoot().addChild(element);
    
    return container;
  }

}
