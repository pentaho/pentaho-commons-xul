package org.pentaho.ui.xul.test.dom;

import java.io.InputStream;

import junit.framework.TestCase;

import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;


/**
 * @author NBaker
 *
 */
public class TestDomFactoryFailures extends TestCase{
  org.dom4j.Document testDoc;
  public void setUp() throws Exception{
    DocumentFactory.registerDOMClass(Object.class);
    DocumentFactory.registerElementClass(Object.class);
    InputStream in = getClass().getResourceAsStream("/resource/documents/sampleXul.xul");
    SAXReader rdr = new SAXReader();
    testDoc = rdr.read(in);
  }

  public void testCreateDocument(){
    try{
      Document doc = DocumentFactory.createDocument();
      assertTrue(doc instanceof Document);
      fail("Should have thrown an exception");
    } catch(Exception e){
    }
  }

  public void testCreateDocumentFromDoc(){
    try{
      Document doc = DocumentFactory.createDocument(testDoc);
      assertTrue(doc instanceof Document);
      fail("Should have thrown an exception");
    } catch(Exception e){
    }
  }

  public void testCreateElement(){
    try{
      Element ele = DocumentFactory.createElement("testelement",null);
      assertTrue(ele instanceof Element && ele.getName().equals("testelement"));
      fail("Should have thrown an exception");
    } catch(Exception e){
    }
  }

  public void testCreateElement2(){
    try{
      Element ele = DocumentFactory.createElement("testelement");
      assertTrue(ele instanceof Element && ele.getName().equals("testelement"));
      fail("Should have thrown an exception");
    } catch(Exception e){
    }
  }
  
}
