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

package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;

public class SwtTextboxTest {

  Document doc = null;
  XulDomContainer container;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/textboxtest.xul" );
    doc = container.getDocumentRoot();

  }

  @Test
  public void maxLengthTest() {
    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox1" );

    assertEquals( 10, txt.getMaxlength() );
    txt.setMaxlength( 15 );
    assertEquals( 15, txt.getMaxlength() );
  }

  @Test
  public void maxTest() {
    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox3" );
    assertEquals( "500", txt.getMax() );
  }

  @Test
  public void minTest() {
    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox3" );
    assertEquals( "4", txt.getMin() );
  }

  @Test
  public void disabledText() {
    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox2" );
    assertTrue( txt.isDisabled() );
    txt.setDisabled( false );
    assertTrue( !txt.isDisabled() );

  }

  @Test
  public void multilineTest() {
    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox1" );
    assertTrue( txt.isMultiline() );
    txt = (XulTextbox) doc.getElementById( "textbox2" );
    assertTrue( !txt.isMultiline() );
  }

  @Test
  public void readonlyTest() {
    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox1" );
    assertTrue( txt.isReadonly() );
  }

  @Test
  public void getTypeTest() throws Exception {

    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox3" );
    assertEquals( "NUMERIC", txt.getType() );

    txt = (XulTextbox) doc.getElementById( "textbox1" );
    assertEquals( "NORMAL", txt.getType() );
  }

  @Test
  public void getValue() throws Exception {

    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox1" );
    assertEquals( "default", txt.getValue() );

  }

  // @Test
  // public void numericTest() throws Exception{
  //
  // XulTextbox txt = (XulTextbox) doc.getElementById("textbox3");
  //
  // txt.setValue("asd");
  // assertEquals("", txt.getValue());
  // txt.setValue("-");
  // assertEquals("-", txt.getValue());
  // txt.setValue("-12");
  // assertEquals("-12", txt.getValue());
  // txt.setValue("10.5");
  // assertEquals("10.5", txt.getValue());
  // txt.setValue("600");
  // assertEquals("", txt.getValue());
  // }

  @Test
  public void selectAllTest() throws Exception {

    XulTextbox txt = (XulTextbox) doc.getElementById( "textbox3" );
    txt.selectAll();
  }

}
