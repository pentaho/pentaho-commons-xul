/**
 * 
 */
package org.pentaho.ui.xul.tests.parser;

import java.io.InputStream;

import junit.framework.TestCase;

import org.dom4j.io.SAXReader;
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
public class TestSwingXulParser extends TestCase{
  org.dom4j.Document testDoc;

  XulDomContainer container;
  
  private XulParser parser;
  public void setUp() throws Exception{
    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);
    InputStream in = getClass().getResourceAsStream("/resource/documents/allTags.xul");
    SAXReader rdr = new SAXReader();
    final org.dom4j.Document doc = rdr.read(in);
    
    
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


    container = new XulWindowContainer();
    parser.setContainer(container);
    parser.parseDocument(testDoc.getRootElement());
  }

  public void testRootElement() throws Exception{
    Document root = container.getDocumentRoot();
    assertNotNull(root);
  }
  
  public void testRootIsWindow() throws Exception{
    Document root = container.getDocumentRoot();
    assertTrue(root.getRootElement() instanceof XulWindow);
  }
  
  public void testElementsByXPath() throws Exception{
    Document root = container.getDocumentRoot();
    assertTrue(
        root.getElementByXPath("/window") != null &&
        root.getElementByXPath("/window/button") != null &&
        root.getElementByXPath("/window/vbox") != null &&
        root.getElementByXPath("/window/vbox/button") != null &&
        root.getElementByXPath("/window/label") != null &&
        root.getElementByXPath("/window/hbox") != null &&
        root.getElementByXPath("/window/hbox/label") != null &&
        root.getElementByXPath("/window/textbox") != null &&
        root.getElementByXPath("/window/script") != null &&
        root.getElementByXPath("/window/spacer") != null &&
        root.getElementByXPath("/window/checkbox") != null &&
        root.getElementByXPath("/window/groupbox") != null &&
        root.getElementByXPath("/window/groupbox/checkbox") != null &&
        root.getElementByXPath("/window/caption") != null &&
        root.getElementByXPath("/window/listbox") != null &&
        root.getElementByXPath("/window/listbox/listitem") != null
    );
  }

}
