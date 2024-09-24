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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.eclipse.swt.widgets.Button;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.swt.SwtXulLoader;

public class SwtCheckboxTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulCheckbox check;
  XulCheckbox check2;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/allTags.xul" );

    check = (XulCheckbox) container.getDocumentRoot().getElementById( "checkbox" );
    check2 = (XulCheckbox) container.getDocumentRoot().getElementById( "checkbox2" );

  }

  @Test
  public final void testLabel() {
    assertEquals( "test", check.getLabel() );
  }

  @Test
  public final void testCommand() {
    assertEquals( "foo.test()", check.getCommand() );
  }

  @Test
  public final void testIsChecked() {
    assertTrue( check.isChecked() );
  }

  @Test
  public final void testSetChecked() {
    check2.setChecked( false );
    assertTrue( !check2.isChecked() );
    check2.setChecked( true );
    assertTrue( check2.isChecked() );

  }

  @Test
  public final void testIsDisabled() {
    assertTrue( !check.isDisabled() );
  }

  @Test
  public final void testSetDisabled() {
    check.setDisabled( true );
    assertTrue( check.isDisabled() );
    check.setDisabled( false );
    assertTrue( !check.isDisabled() );
  }

  @Test
  public final void setManagedObject() {
    assertTrue( check.getManagedObject() instanceof Button );
  }

}
