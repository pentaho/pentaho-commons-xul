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

package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.util.Locale;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.binding.BindingExpression;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.samples.BindingEventHandler;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class BindingsTest {

  private static XulDomContainer container;
  private static BindingEventHandler handler;

  @BeforeClass
  public static void setup() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    Locale.setDefault( Locale.US );
    container = new SwingXulLoader().loadXul( "documents/bindingsTest.xul" );
    container.initialize();
    handler = (BindingEventHandler) container.getEventHandler( "handler" );
  }

  @Test
  public void testSimpleBinding() throws Exception {
    handler.test();
    XulLabel firstName = (XulLabel) container.getDocumentRoot().getElementById( "firstName" );
    assertEquals( "Oliver", firstName.getValue() );
  }

  @Test
  public void testModelBoolean() throws Exception {
    handler.toggleBoolean();
    XulButton button = (XulButton) container.getDocumentRoot().getElementById( "disabledButton" );
    assertTrue( !button.isDisabled() );

  }

  @Test
  public void testListTreeBind() throws Exception {
    handler.addProduct();
    XulTree table = (XulTree) container.getDocumentRoot().getElementById( "productTable" );

    assertEquals( 1, table.getRows() );
    handler.addProduct();
    assertEquals( 2, table.getRows() );
  }

  @Test
  public void testConversion() throws Exception {
    XulTextbox degrees = (XulTextbox) container.getDocumentRoot().getElementById( "degreesField" );
    XulTextbox radians = (XulTextbox) container.getDocumentRoot().getElementById( "radiansField" );
    degrees.setValue( "45" );
    assertEquals( "0.79", radians.getValue() );
    radians.setValue( "1.575" );
    assertEquals( 90, Math.round( Float.parseFloat( degrees.getValue() ) ) );
  }

  @Test
  public void testExpression() throws Exception {
    BindingExpression exp = BindingExpression.parse( "foo=bar.baz" );
    assertEquals( "foo", exp.sourceAttr );
    assertEquals( "bar", exp.target );
    assertEquals( "baz", exp.targetAttr );
  }

  @Test( expected = IllegalArgumentException.class )
  public void testExpressionFailure() throws Exception {
    BindingExpression.parse( "foo23453.203-baz" );
  }

  @Test
  public void testRemove() throws Exception {
    handler.model.setFirstName( "Ted" );
    XulLabel firstName = (XulLabel) container.getDocumentRoot().getElementById( "firstName" );
    assertEquals( "Ted", firstName.getValue() );
    container.removeBinding( handler.removeMeBinding );
    handler.model.setFirstName( "John" );
    assertEquals( "Ted", firstName.getValue() );
  }
}
