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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.eclipse.swt.custom.CLabel;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtLabel;

public class SwtLabelTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulLabel label;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/labeltest.xul" );

    runner = new SwtXulRunner();
    runner.addContainer( container );
    label = (XulLabel) container.getDocumentRoot().getElementById( "label-one" );

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

  @Test
  public void testLink() {
    SwtLabel swtLabel = (SwtLabel) container.getDocumentRoot().getElementById( "label-four" );
    assertThat( swtLabel.getManagedObject(), instanceOf( org.eclipse.swt.widgets.Link.class ) );
    assertThat( swtLabel.getOnclick(), equalTo( "doIt()" ) );
  }

  @Test
  public void testMultilineLabel() {
    SwtLabel swtLabel = (SwtLabel) container.getDocumentRoot().getElementById( "label-three" );
    assertThat( swtLabel.getManagedObject(), instanceOf( org.eclipse.swt.widgets.Label.class ) );
  }

  @Test
  public void testSinglelineLabel() {
    SwtLabel swtLabel = (SwtLabel) container.getDocumentRoot().getElementById( "label-two" );
    assertThat( swtLabel.getManagedObject(), instanceOf( CLabel.class ) );
  }


}
