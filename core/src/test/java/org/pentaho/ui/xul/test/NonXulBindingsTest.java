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


package org.pentaho.ui.xul.test;

import org.junit.Test;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.DefaultBinding;
import org.pentaho.ui.xul.samples.BindingBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NonXulBindingsTest {

  @Test
  public void testObjectToObjectForwardBinding() {
    BindingBean a = new BindingBean();
    BindingBean b = new BindingBean();
    Binding binding = new DefaultBinding( a, "property1", b, "property1" );
    binding.bindForward();

    a.setProperty1( "abc" );

    assertEquals( "abc", b.getProperty1() );
  }

  @Test
  public void testObjectToObjectReverseBinding() {
    BindingBean a = new BindingBean();
    BindingBean b = new BindingBean();
    Binding binding = new DefaultBinding( a, "property1", b, "property1" );
    binding.bindReverse();

    a.setProperty1( "abc" );
    assertNull( b.getProperty1() );

    b.setProperty1( "def" );
    assertEquals( "def", a.getProperty1() );
  }

}
