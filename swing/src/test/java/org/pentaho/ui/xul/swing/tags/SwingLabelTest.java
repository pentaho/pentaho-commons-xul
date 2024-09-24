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
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingLabelTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulLabel label;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/labeltest.xul" );

    runner = new SwingXulRunner();
    runner.addContainer( container );
    label = (XulLabel) container.getDocumentRoot().getElementById( "label-one" );

  }

  @After
  public void tearDown() throws Exception {
    if ( runner != null ) {
      runner.stop();
    }
  }

  @Test
  public void testValue() throws Exception {
    assertEquals( "First Label", label.getValue() );
    label.setValue( "testing..." );
    assertEquals( "testing...", label.getValue() );

  }

  @Test
  public void testDisable() throws Exception {
    assertTrue( !label.isDisabled() );
    label.setDisabled( true );
    assertTrue( label.isDisabled() );

  }

}
