/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.test.dom;

import java.io.InputStream;

import junit.framework.TestCase;

import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;

/**
 * @author NBaker
 * 
 */
public class TestDomFactoryFailures extends TestCase {
  org.dom4j.Document testDoc;

  public void setUp() throws Exception {
    DocumentFactory.registerDOMClass( Object.class );
    DocumentFactory.registerElementClass( Object.class );
    InputStream in = getClass().getResourceAsStream( "/resource/documents/sampleXul.xul" );
    SAXReader rdr = new SAXReader();
    testDoc = rdr.read( in );
  }

  public void testCreateDocument() {
    try {
      Document doc = DocumentFactory.createDocument();
      assertTrue( doc instanceof Document );
      fail( "Should have thrown an exception" );
    } catch ( Exception e ) {
      //ignored
    }
  }

  public void testCreateDocumentFromDoc() {
    try {
      Document doc = DocumentFactory.createDocument( testDoc );
      assertTrue( doc instanceof Document );
      fail( "Should have thrown an exception" );
    } catch ( Exception e ) {
      //ignored
    }
  }

  public void testCreateElement() {
    try {
      Element ele = DocumentFactory.createElement( "testelement", null );
      assertTrue( ele instanceof Element && ele.getName().equals( "testelement" ) );
      fail( "Should have thrown an exception" );
    } catch ( Exception e ) {
      //ignored
    }
  }

  public void testCreateElement2() {
    try {
      Element ele = DocumentFactory.createElement( "testelement" );
      assertTrue( ele instanceof Element && ele.getName().equals( "testelement" ) );
      fail( "Should have thrown an exception" );
    } catch ( Exception e ) {
      //ignored
    }
  }

}
