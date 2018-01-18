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
 * Copyright (c) 2002-2018 Hitachi Vantara.  All rights reserved.
 */

package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.GraphicsEnvironment;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.util.Orient;

public class SwtElementTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  SwtElement element;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    InputStream in = SwtXulRunner.class.getClassLoader().getResourceAsStream( "documents/splitterTest.xul" );
    assertNotNull( "XUL input not found.", in );
    SAXReader rdr = new SAXReader();
    final Document doc = rdr.read( in );

    container = new SwtXulLoader().loadXul( doc );

    runner = new SwtXulRunner();
    runner.addContainer( container );
    element = (SwtElement) container.getDocumentRoot().getElementById( "testContainerElement" );
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
  @Ignore
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
  @Ignore
  public final void testSetDisabled() {
    element.setDisabled( true );
  }

  @Test
  @Ignore
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
    try {
      element.getFirstChild();

      XulComponent newComp = element.getDocument().createElement( "button" );
      XulComponent badComp = element.getDocument().createElement( "button" );
      element.replaceChild( badComp, newComp );
      fail( "You should have thrown a XulDomException" );
    } catch ( Exception e ) {
    }
  }

}
