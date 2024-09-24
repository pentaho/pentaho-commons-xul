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

package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertNotNull;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtHboxTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulHbox hbox;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/imageButton.xul" );

    runner = new SwtXulRunner();
    runner.addContainer( container );
    hbox = (XulHbox) container.getDocumentRoot().getElementByXPath( "/window/hbox" );

  }

  @Test
  public void testReplaceChild() throws Exception {
    XulButton btn = (XulButton) hbox.getElementByXPath( "button" );

    XulCheckbox check = (XulCheckbox) hbox.getDocument().createElement( "checkbox" );

    hbox.replaceChild( btn, check );

    XulCheckbox check2 = (XulCheckbox) hbox.getDocument().getElementByXPath( "/window/hbox/checkbox" );

    assertNotNull( check2 );
  }

}
