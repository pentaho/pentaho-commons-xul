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
 * Copyright (c) 2002-2018 Hitachi Vantara.  All rights reserved.
 */

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
