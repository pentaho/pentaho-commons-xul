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
