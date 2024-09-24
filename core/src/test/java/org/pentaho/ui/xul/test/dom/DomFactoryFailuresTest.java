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
import org.pentaho.ui.xul.dom.DocumentFactory;

/**
 * @author NBaker
 * 
 */
public class DomFactoryFailuresTest {
  static org.dom4j.Document testDoc;

  @BeforeClass
  public static void setUp() throws Exception {
    DocumentFactory.registerDOMClass( Object.class );
    DocumentFactory.registerElementClass( Object.class );
    InputStream in = DomFactoryFailuresTest.class.getClassLoader().getResourceAsStream( "documents/sampleXul.xul" );
    SAXReader rdr = new SAXReader();
    testDoc = rdr.read( in );
  }


  @Test( expected = XulException.class )
  public void testCreateDocument() throws XulException {
    DocumentFactory.createDocument();
  }

  @Test( expected = XulException.class )
  public void testCreateDocumentFromDoc() throws XulException {
    DocumentFactory.createDocument( testDoc );
  }

  @Test( expected = XulException.class )
  public void testCreateElement() throws XulException {
    DocumentFactory.createElement( "testelement", null );
  }

  @Test( expected = XulException.class )
  public void testCreateElement2() throws XulException {
    DocumentFactory.createElement( "testelement" );
  }

}
