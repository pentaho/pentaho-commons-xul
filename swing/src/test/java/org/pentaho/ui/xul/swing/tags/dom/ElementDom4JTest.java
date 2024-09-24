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

package org.pentaho.ui.xul.swing.tags.dom;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.util.List;

import org.junit.After;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class ElementDom4JTest {
  private static Element ele;

  @BeforeClass
  public static void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    XulDomContainer container = new SwingXulLoader().loadXul( "documents/splitterTest.xul" );
    ele = container.getDocument( 0 ).getElementById( "testContainerElement" );
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testGetName() {
    assertTrue( ele.getName().equalsIgnoreCase( "vbox" ) );
  }

  @Test
  public void testGetDocument() {
    assertNotNull( ele.getDocument() );
  }

  @Test
  public void testGetParent() {
    assertNotNull( ele.getParent().getName().equals( "window" ) );
  }

  @Test
  public void getFirstChild() {
    assertNotNull( ele.getFirstChild().getName().equals( "groupbox" ) );
  }

  @Test
  public void getChildNodes() {
    List<XulComponent> children = ele.getChildNodes();
    assertNotNull( children );
    assertTrue( children.size() == 3 );
  }

  // TODO: add better test once we have a custom pentaho element
  @Test
  public void getNamespace() {
    Namespace name = ele.getNamespace();
    assertNotNull( name );
  }

  @Test
  public void setNamespace() {
    ele.setNamespace( "pen", "www.pentaho.com/xul.xml" );
  }

  @Test
  public void getElementByID() {
    XulComponent btn = ele.getElementById( "button1" );
    assertNotNull( btn );
    assertTrue( btn.getName().equals( "button" ) );
  }

  @Test
  public void getElementByXPath() {
    XulComponent btn = ele.getElementByXPath( "hbox/button" );
    assertNotNull( btn );
    assertTrue( btn.getName().equals( "button" ) );
  }

}
