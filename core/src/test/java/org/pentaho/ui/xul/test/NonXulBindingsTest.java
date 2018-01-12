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
