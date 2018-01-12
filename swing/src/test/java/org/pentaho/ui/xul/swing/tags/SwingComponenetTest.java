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

import java.awt.GraphicsEnvironment;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingComponenetTest {
  XulDomContainer container;
  XulButton button;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/componentTest.xul" );
    button = (XulButton) container.getDocumentRoot().getElementById( "testButton" );
  }

  @Test
  public final void testGetSetOnBlur() {
    assertEquals( "foo.bar()", button.getOnblur() );
    button.setOnblur( "baz.bang()" );
    assertEquals( "baz.bang()", button.getOnblur() );
  }

  @Test
  public final void testGetSetPadding() {
    assertEquals( 5, button.getPadding() );
    button.setPadding( 6 );
    assertEquals( 6, button.getPadding() );
  }

  @Test
  public final void testGetSetBgcolor() {
    assertEquals( "#fcfcfc", button.getBgcolor() );
    button.setBgcolor( "#cccccc" );
    assertEquals( "#cccccc", button.getBgcolor() );
  }

}
