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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.GraphicsEnvironment;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.util.Orient;

public class SwingElementTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  SwingElement element;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream( "documents/splitterTest.xul" );
    assertNotNull( "XUL input not found.", in );
    SAXReader rdr = new SAXReader();
    final Document doc = rdr.read( in );

    container = new SwingXulLoader().loadXul( doc );

    runner = new SwingXulRunner();
    runner.addContainer( container );

    element = (SwingElement) container.getDocumentRoot().getElementById( "testContainerElement" );
  }

  @After
  public void tearDown() throws Exception {
    if ( runner != null ) {
      runner.stop();
    }
  }

  @Test
  public final void testOrient() {
    element.setOrient( "vertical" );
    String getVal = element.getOrient();
    assertTrue( getVal.equalsIgnoreCase( "vertical" ) );
  }

  @Test
  public final void testVerticalOrientation() {
    element.setOrient( "vertical" );
    Orient val = element.getOrientation();
    assertEquals( val, Orient.VERTICAL );
  }

  @Test
  public final void testHorizontalOrientation() {
    element.setOrient( "horizontal" );
    Orient val = element.getOrientation();
    assertEquals( val, Orient.HORIZONTAL );
  }

  @Test
  public final void testGetJComponent() {
    JComponent c = element.getJComponent();

    assertNotNull( c );
  }

  @Test
  public final void testSetOnBlur() {
    element.setOnblur( "test.foo()" );
  }

  @Test
  public final void testAddChangeListener() {
    element.addPropertyChangeListener( new PropertyChangeListener() {
      public void propertyChange( PropertyChangeEvent arg0 ) {
      }
    } );
  }

  @Test
  public final void testRemoveChangeListener() {
    PropertyChangeListener changeListener = new PropertyChangeListener() {
      public void propertyChange( PropertyChangeEvent arg0 ) {
      }
    };
    element.addPropertyChangeListener( changeListener );
    element.removePropertyChangeListener( changeListener );
  }

  @Test
  public final void testSetDisabled() {
    element.setDisabled( true );
  }

  @Test
  public final void testGetDisabled() {
    element.setDisabled( true );
    boolean b1 = element.isDisabled();

    element.setDisabled( false );
    boolean b2 = element.isDisabled();

    assertTrue( b1 && !b2 );

  }

  @Test
  public final void testReplaceChild() throws Exception {
    XulComponent firstChild = element.getFirstChild();

    XulComponent newComp = element.getDocument().createElement( "button" );
    element.replaceChild( firstChild, newComp );

  }

  @Test
  public final void testReplaceChildFailure() throws Exception {
    // assertThrows(XulDomException.class){
    // public void test() throws Exception {
    // XulComponent firstChild = element.getFirstChild();
    //
    // XulComponent newComp = element.getDocument().createElement("button");
    // XulComponent badComp = element.getDocument().createElement("button");
    // element.replaceChild(badComp, newComp);
    // }
    // }.runTest();

    try {
      XulComponent firstChild = element.getFirstChild();

      XulComponent newComp = element.getDocument().createElement( "button" );
      XulComponent badComp = element.getDocument().createElement( "button" );
      element.replaceChild( badComp, newComp );
      fail( "You should have thrown a XulDomException" );
    } catch ( XulDomException e ) {
    }

  }

  @Test
  public final void testResetContainer() throws Exception {
    element.resetContainer();

  }

  @Test
  public void testWidthHeight() throws Exception {
    element.setWidth( 222 );
    element.setHeight( 333 );
    assertEquals( 222, element.getWidth() );
    assertEquals( 333, element.getHeight() );
  }

  @Test
  public void testSetAttribute() throws Exception {
    element.setAttribute( "foo", "bar" );
    assertEquals( "bar", element.getAttributeValue( "foo" ) );
  }

  @Test
  public void testSetAttribute2() throws Exception {
    element.setAttribute( new Attribute( "foo", "bar" ) );
    assertEquals( "bar", element.getAttributeValue( "foo" ) );
  }

  @Test
  public void testSetAttributes() throws Exception {
    List<Attribute> attrs = new ArrayList<Attribute>();
    attrs.add( new Attribute( "foo", "bar" ) );
    attrs.add( new Attribute( "baz", "bang" ) );
    element.setAttributes( attrs );
    assertEquals( "bar", element.getAttributeValue( "foo" ) );
    assertEquals( "bang", element.getAttributeValue( "baz" ) );

    List<Attribute> outAttrs = element.getAttributes();
    assertEquals( 4, outAttrs.size() );

  }

}
