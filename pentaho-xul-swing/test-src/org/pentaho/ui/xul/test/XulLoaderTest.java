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

package org.pentaho.ui.xul.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.ResourceBundle;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.impl.XulFragmentContainer;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class XulLoaderTest {

  @Test
  public void testLoadXulFromInputStream() throws Exception {

    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream( "resource/documents/includeTest.xul" );
    Document doc = rdr.read( in );
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir( "resource/documents" );
    XulDomContainer container = loader.loadXul( doc );
    XulComponent testComponent = container.getDocumentRoot().getElementById( "general-settings-box" );
    assertNotNull( testComponent );
  }

  @Test
  public void testLoadXulFromString() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "resource/documents/includeTest.xul" );
    XulComponent testComponent = container.getDocumentRoot().getElementById( "general-settings-box" );
    assertNotNull( testComponent );
  }

  @Test
  public void testLoadXulFragFromInputStream() throws Exception {

    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream( "resource/documents/includeTest.xul" );
    Document doc = rdr.read( in );
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir( "resource/documents" );
    XulDomContainer container = loader.loadXulFragment( doc );
    XulComponent testComponent = container.getDocumentRoot().getElementById( "general-settings-box" );
    assertNotNull( testComponent );
  }

  @Test
  public void testLoadXulFragFromFromString() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXulFragment( "resource/documents/includeTest.xul" );
    XulComponent testComponent = container.getDocumentRoot().getElementById( "general-settings-box" );
    assertNotNull( testComponent );
  }

  @Test
  public void testLoadXulFromStringWithBundle() throws Exception {
    XulLoader loader = new SwingXulLoader();
    ResourceBundle bundle = ResourceBundle.getBundle( "resource/documents/internationalization" );
    XulDomContainer container = loader.loadXul( "resource/documents/internationalization.xul", bundle );
    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById( "welcomeLabel" );
    assertEquals( "Welcome & Hello >< !!", welcome.getValue() );
  }

  @Test
  public void testLoadXulFragFromFromStringWithBundle() throws Exception {
    XulLoader loader = new SwingXulLoader();
    ResourceBundle bundle = ResourceBundle.getBundle( "resource/documents/internationalization" );
    XulDomContainer container = loader.loadXulFragment( "resource/documents/internationalization.xul", bundle );
    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById( "welcomeLabel" );
    assertEquals( "Welcome & Hello >< !!", welcome.getValue() );
  }

  @Test
  public void testProcessOverlay() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "resource/documents/overlayTestProgramatically.xul" );
    loader.processOverlay( "overlay.xul", container.getDocumentRoot(), container );

    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById( "newLabel" );
    assertNotNull( welcome );
  }

  @Test
  public void testRemoveOverlay() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "resource/documents/overlayTestProgramatically.xul" );
    loader.processOverlay( "overlay.xul", container.getDocumentRoot(), container );

    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById( "newLabel" );
    assertNotNull( welcome );

    loader.removeOverlay( "overlay.xul", container.getDocumentRoot(), container );
    welcome = (XulLabel) container.getDocumentRoot().getElementById( "newLabel" );
    assertNull( welcome );

  }

  @Test
  public void testIncludeInternationalization() throws Exception {

    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream( "resource/documents/includeTest.xul" );
    Document doc = rdr.read( in );
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir( "resource/documents" );
    XulDomContainer container = loader.loadXul( doc );
    XulLabel testComponent = (XulLabel) container.getDocumentRoot().getElementById( "nicknameLabel" );
    assertEquals( "Nickname", testComponent.getValue() );
  }

  @Test
  public void testFragErrors() throws Exception {

    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream( "resource/documents/includeTest.xul" );
    Document doc = rdr.read( in );
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir( "resource/documents" );
    XulFragmentContainer container = (XulFragmentContainer) loader.loadXulFragment( doc );

    try {
      container.loadFragment( "foo" );
      fail( "Should have errored" );
    } catch ( XulException e ) {

    }

    assertNull( container.loadFragment( "foo", (ResourceBundle) null ) );
    assertNull( container.getDocument( 3 ) );

  }

}
