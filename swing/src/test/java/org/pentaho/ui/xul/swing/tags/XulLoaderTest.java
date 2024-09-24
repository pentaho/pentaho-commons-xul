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

package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.ResourceBundle;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.impl.XulFragmentContainer;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class XulLoaderTest {

  @Before
  public void setUp() {
    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );
  }

  @Test
  public void testLoadXulFromInputStream() throws Exception {

    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream( "documents/includeTest.xul" );
    Document doc = rdr.read( in );
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir( "documents" );
    XulDomContainer container = loader.loadXul( doc );
    XulComponent testComponent = container.getDocumentRoot().getElementById( "general-settings-box" );
    assertNotNull( testComponent );
  }

  @Test
  public void testLoadXulFromString() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/includeTest.xul" );
    XulComponent testComponent = container.getDocumentRoot().getElementById( "general-settings-box" );
    assertNotNull( testComponent );
  }

  @Test
  public void testLoadXulFragFromInputStream() throws Exception {

    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream( "documents/includeTest.xul" );
    Document doc = rdr.read( in );
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir( "documents" );
    XulDomContainer container = loader.loadXulFragment( doc );
    XulComponent testComponent = container.getDocumentRoot().getElementById( "general-settings-box" );
    assertNotNull( testComponent );
  }

  @Test
  public void testLoadXulFragFromFromString() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXulFragment( "documents/includeTest.xul" );
    XulComponent testComponent = container.getDocumentRoot().getElementById( "general-settings-box" );
    assertNotNull( testComponent );
  }

  @Test
  public void testLoadXulFromStringWithBundle() throws Exception {
    XulLoader loader = new SwingXulLoader();
    ResourceBundle bundle = ResourceBundle.getBundle( "documents/internationalization" );
    XulDomContainer container = loader.loadXul( "documents/internationalization.xul", bundle );
    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById( "welcomeLabel" );
    assertEquals( "Welcome & Hello >< !!", welcome.getValue() );
  }

  @Test
  public void testLoadXulFragFromFromStringWithBundle() throws Exception {
    XulLoader loader = new SwingXulLoader();
    ResourceBundle bundle = ResourceBundle.getBundle( "documents/internationalization" );
    XulDomContainer container = loader.loadXulFragment( "documents/internationalization.xul", bundle );
    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById( "welcomeLabel" );
    assertEquals( "Welcome & Hello >< !!", welcome.getValue() );
  }

  @Test
  public void testProcessOverlay() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/overlayTestProgramatically.xul" );
    loader.processOverlay( "overlay.xul", container.getDocumentRoot(), container );

    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById( "newLabel" );
    assertNotNull( welcome );
  }

  @Test
  public void testRemoveOverlay() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/overlayTestProgramatically.xul" );
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
    InputStream in = getClass().getClassLoader().getResourceAsStream( "documents/includeTest.xul" );
    Document doc = rdr.read( in );
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir( "documents" );
    XulDomContainer container = loader.loadXul( doc );
    XulLabel testComponent = (XulLabel) container.getDocumentRoot().getElementById( "nicknameLabel" );
    assertEquals( "Nickname", testComponent.getValue() );
  }

  @Test
  public void testFragErrors() throws Exception {

    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream( "documents/includeTest.xul" );
    Document doc = rdr.read( in );
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir( "documents" );
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
