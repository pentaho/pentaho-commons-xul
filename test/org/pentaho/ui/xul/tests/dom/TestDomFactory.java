/**
 * 
 */
package org.pentaho.ui.xul.tests.dom;

import java.io.InputStream;

import junit.framework.TestCase;

import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;

/**
 * @author OEM
 *
 */
public class TestDomFactory extends TestCase{
  org.dom4j.Document testDoc;
  public void setUp() throws Exception{
    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);
    InputStream in = getClass().getResourceAsStream("/resource/documents/samplexul.xul");
    testDoc = CleanXmlHelper.getDocFromStream(in);
  }

  public void testCreateDocument(){
    try{
      Document doc = DocumentFactory.createDocument();
      assertTrue(doc instanceof Document);
    } catch(Exception e){
      fail();
    }
  }

  public void testCreateDocumentFromDoc(){
    try{
      Document doc = DocumentFactory.createDocument(testDoc);
      assertTrue(doc instanceof Document);
    } catch(Exception e){
      fail();
    }
  }

  public void testCreateElement(){
    try{
      Element ele = DocumentFactory.createElement("testelement",null);
      assertTrue(ele instanceof Element && ele.getName().equals("testelement"));
    } catch(Exception e){
      fail();
    }
  }
}
