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
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

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
