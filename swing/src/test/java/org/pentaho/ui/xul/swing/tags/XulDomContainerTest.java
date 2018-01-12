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
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.util.Map;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.impl.XulEventHandler;
import org.pentaho.ui.xul.samples.SampleEventHandler;
import org.pentaho.ui.xul.samples.TreeHandler;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class XulDomContainerTest {

  @Before
  public void setUp() {
    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );
  }

  @Test
  public void testOnLoad() throws Exception {

    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/testEventHandlers.xul" );
    XulTextbox testComponent = (XulTextbox) container.getDocumentRoot().getElementById( "textbox1" );
    assertEquals( "initial value", testComponent.getValue() );
    container.initialize();
    assertEquals( "new value", testComponent.getValue() );
  }

  @Test
  public void testGetHandlers() throws Exception {

    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/testEventHandlers.xul" );
    Map<String, XulEventHandler> handlers = container.getEventHandlers();
    assertTrue( handlers.size() == 2 );
    assertNotNull( handlers.get( "handler1" ) );
    assertNotNull( handlers.get( "handler2" ) );
  }

  @Test
  public void testGetHandler() throws Exception {

    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/testEventHandlers.xul" );
    assertNotNull( container.getEventHandler( "handler1" ) );
    assertNotNull( container.getEventHandler( "handler2" ) );
  }

  @Test
  public void testEventHandlerParams() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/testEventHandlers.xul" );
    container.initialize();
    SampleEventHandler handler = (SampleEventHandler) container.getEventHandler( "handler2" );
    assertEquals( 5, handler.testNumber );
    assertEquals( 5.2, handler.testDouble, .01 );
    assertEquals( "hello", handler.testString );
    assertTrue( handler.testBool );
  }

  @Test
  public void testMergeContainers() throws Exception {
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/testEventHandlers.xul" );
    XulDomContainer container2 = container.loadFragment( "documents/testEventHandlers2.xul" );
    container.mergeContainer( container2 );
    assertNotNull( container.getEventHandler( "handler3" ) );

  }

  @Test
  public void testIsRegistered() throws Exception {

    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/testEventHandlers.xul" );
    assertTrue( container.isRegistered( "BUTTON" ) );
  }

  @Test
  public void testRegisterHandler() throws Exception {

    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/testEventHandlers.xul" );
    TreeHandler handler = new TreeHandler();
    handler.setName( "handler1" );
    container.addEventHandler( handler );
    TreeHandler outHandler = (TreeHandler) container.getEventHandler( "handler1" );
    assertNotNull( outHandler );
    assertSame( handler, outHandler );
  }

  @Test
  public void testBindings() throws Exception {

    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul( "documents/testEventHandlers.xul" );
    // container.createBinding(source, sourceAttr, targetId, targetAttr)
  }

}
