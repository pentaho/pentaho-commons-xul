/**
 * 
 */
package org.pentaho.ui.xul.tests.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulParser;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;

/**
 * @author OEM
 *
 */
public class TestSwtXulParser extends TestCase{
  org.dom4j.Document testDoc;

  XulWindowContainer container;
  
  private XulParser parser;
  public void setUp() throws Exception{
    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);
    InputStream in = getClass().getResourceAsStream("/resource/documents/allTags.xul");
    testDoc = CleanXmlHelper.getDocFromStream(in);
    
    
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
    parser.registerHandler("SCRIPT", "org.pentaho.ui.xul.swt.tags.SwtScript");
    parser.registerHandler("CHECKBOX", "org.pentaho.ui.xul.swt.tags.SwtCheckbox");


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
    assertTrue(root.getRootElement().getXulElement() instanceof XulWindow);
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
