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


package org.pentaho.ui.xul.test.dom;

import java.io.InputStream;

import org.dom4j.io.SAXReader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author NBaker
 * 
 */
public class DomFactoryTest {
  static org.dom4j.Document testDoc;

  @BeforeClass
  public static void setUp() throws Exception {
    DocumentFactory.registerDOMClass( DocumentDom4J.class );
    DocumentFactory.registerElementClass( ElementDom4J.class );
    InputStream in = DomFactoryTest.class.getClassLoader().getResourceAsStream( "documents/samplexul2.xul" );
    SAXReader rdr = new SAXReader();
    testDoc = rdr.read( in );
  }

  @Test
  public void testCreateDocument() throws XulException {
    Document doc = DocumentFactory.createDocument();
    assertThat( doc, instanceOf( Document.class ) );
  }

  @Test
  public void testCreateDocumentFromDoc() throws XulException {
    Document doc = DocumentFactory.createDocument( testDoc );
    assertThat( doc, instanceOf( Document.class ) );
  }

  @Test
  public void testCreateElement() throws XulException {
    Element ele = DocumentFactory.createElement( "testelement", null );
    assertThat( ele, instanceOf( Element.class ) );
    assertEquals( "testelement", ele.getName() );
  }
}
