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

package org.pentaho.ui.xul.swing.tags.binding;

import static org.junit.Assert.assertEquals;

import java.awt.GraphicsEnvironment;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingConvertor;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.binding.DefaultBindingFactory;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class BindingTest {
  XulRunner runner = null;
  Document document = null;
  XulDomContainer container;

  TestClass a;
  TestClass b;

  BindingFactory bf;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/imageButton.xul" );

    runner = new SwingXulRunner();
    runner.addContainer( container );

    document = container.getDocumentRoot();

    a = new TestClass();
    b = new TestClass();

    bf = new DefaultBindingFactory();
    bf.setDocument( container.getDocumentRoot() );
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public final void testStringBinding() {
    bf.createBinding( a, "property1", b, "property1" );

    a.setProperty1( "testing" );
    assertEquals( "testing", b.getProperty1() );

    b.setProperty1( "testing back" );
    assertEquals( "testing back", a.getProperty1() );
  }

  @Test
  public final void testBooleanBinding() {
    bf.createBinding( a, "property2", b, "property2" );

    a.setProperty2( true );
    assertEquals( true, b.isProperty2() );

    b.setProperty2( false );
    assertEquals( false, a.isProperty2() );
  }

  @Test
  public final void testBooleanNegation() {
    bf.createBinding( a, "property2", b, "!property2" );

    a.setProperty2( true );
    assertEquals( false, b.isProperty2() );

    b.setProperty2( false );
    assertEquals( true, a.isProperty2() );
  }

  @Test
  public final void testDoubleBooleanNegation() {
    bf.createBinding( a, "!property2", b, "!property2" );

    a.setProperty2( true );
    assertEquals( true, b.isProperty2() );

    b.setProperty2( false );
    assertEquals( false, a.isProperty2() );
  }

  @Test
  public final void testConvertor() {
    bf.createBinding( a, "property1", b, "property2", new BindingConvertor<String, Boolean>() {

      @Override
      public Boolean sourceToTarget( String value ) {
        return value.equalsIgnoreCase( "true" );
      }

      @Override
      public String targetToSource( Boolean value ) {
        return value.toString();
      }
    } );

    a.setProperty1( "true" );
    assertEquals( true, b.isProperty2() );

    b.setProperty2( false );
    assertEquals( "false", a.getProperty1() );
  }

  @Test
  public final void testOneWayBinding() {
    bf.setBindingType( Binding.Type.ONE_WAY );
    bf.createBinding( a, "property1", b, "property1" );

    a.setProperty1( "testing" );
    assertEquals( "testing", b.getProperty1() );

    b.setProperty1( "testing back" );
    assertEquals( "testing", a.getProperty1() );
  }

  @Test
  public final void testFactory1() {
    XulButton btn1 = (XulButton) document.getElementById( "firstButton" );
    XulButton btn2 = (XulButton) document.getElementById( "secondButton" );

    bf.createBinding( "firstButton", "disabled", "secondButton", "disabled" );

    btn1.setDisabled( true );
    assertEquals( true, btn2.isDisabled() );

    btn2.setDisabled( false );
    assertEquals( false, btn1.isDisabled() );
  }

  @Test
  public final void testFactory2() {
    XulButton btn1 = (XulButton) document.getElementById( "firstButton" );
    XulButton btn2 = (XulButton) document.getElementById( "secondButton" );

    bf.createBinding( btn1, "disabled", "secondButton", "disabled" );

    btn1.setDisabled( true );
    assertEquals( true, btn2.isDisabled() );

    btn2.setDisabled( false );
    assertEquals( false, btn1.isDisabled() );
  }

  @Test
  public final void testFactory3() {
    XulButton btn1 = (XulButton) document.getElementById( "firstButton" );
    XulButton btn2 = (XulButton) document.getElementById( "secondButton" );

    bf.createBinding( "firstButton", "disabled", btn2, "disabled" );

    btn1.setDisabled( true );
    assertEquals( true, btn2.isDisabled() );

    btn2.setDisabled( false );
    assertEquals( false, btn1.isDisabled() );
  }

  public class TestClass extends XulEventSourceAdapter {
    private String property1;
    private boolean property2;

    public TestClass() {

    }

    public String getProperty1() {
      return property1;
    }

    public void setProperty1( String property1 ) {
      String oldVal = this.property1;
      this.property1 = property1;
      this.firePropertyChange( "property1", oldVal, this.property1 );

    }

    public boolean isProperty2() {

      return property2;
    }

    public void setProperty2( boolean property2 ) {
      Boolean oldVal = this.property2;
      this.property2 = property2;
      this.firePropertyChange( "property2", oldVal, this.property2 );
    }

  }

}
