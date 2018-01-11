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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.dom4j.Document;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtDialog;

public class SwtDialogTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulDialog dialog;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/dialogs.xul" );

    runner = new SwtXulRunner();
    runner.addContainer( container );
    dialog = (XulDialog) container.getDocumentRoot().getElementById( "dialog" );

  }

  @Test
  public void testGetButtons() throws Exception {
    assertEquals( "ACCEPT,CANCEL,EXTRA1,EXTRA2", dialog.getButtons() );
  }

  @Test
  public void testSetButtons() throws Exception {
    dialog.setButtons( "accept, cancel, extra1, extra2" );
    dialog.setOndialogextra1( "foo.bar()" );
    dialog.setOndialogextra2( "foo.bar()" );
    dialog.setButtonlabelextra1( "extra1" );
    dialog.setButtonlabelextra2( "extra2" );

    ( (SwtDialog) dialog ).setButtons();
    assertEquals( "ACCEPT,CANCEL,EXTRA1,EXTRA2", dialog.getButtons() );
  }

  @Test
  public void testGetButtonlabelcancel() throws Exception {
    assertEquals( "Cancel", dialog.getButtonlabelcancel() );
  }

  @Test
  public void testSetButtonlabelcancel() throws Exception {
    dialog.setButtonlabelcancel( "cancel me please" );
    assertEquals( "cancel me please", dialog.getButtonlabelcancel() );
  }

  @Test
  public void testGetButtonlabelaccept() throws Exception {
    assertEquals( "Save", dialog.getButtonlabelaccept() );
  }

  @Test
  public void testSetButtonlabelaccept() throws Exception {
    dialog.setButtonlabelaccept( "save me please" );
    assertEquals( "save me please", dialog.getButtonlabelaccept() );
  }

  @Test
  public void testGetButtonlabelextra1() throws Exception {
    assertEquals( "extra1", dialog.getButtonlabelextra1() );
  }

  @Test
  public void testSetButtonlabelextra1() throws Exception {
    dialog.setButtonlabelextra1( "this is extra1" );
    assertEquals( "this is extra1", dialog.getButtonlabelextra1() );
  }

  @Test
  public void testGetButtonlabelextra2() throws Exception {
    assertEquals( "extra2", dialog.getButtonlabelextra2() );
  }

  @Test
  public void testSetButtonlabelextra2() throws Exception {
    dialog.setButtonlabelextra2( "this is extra2" );
    assertEquals( "this is extra2", dialog.getButtonlabelextra2() );
  }

  @Test
  public void testGetOndialogaccept() throws Exception {
    assertEquals( "onAccept", dialog.getOndialogaccept() );
  }

  @Test
  public void testGetOndialogcancel() throws Exception {
    assertEquals( "onCancel", dialog.getOndialogcancel() );
  }

  @Test
  public void testGetOndialogextra1() throws Exception {
    assertEquals( "onExtra1", dialog.getOndialogextra1() );
  }

  @Test
  public void testGetOndialogextra2() throws Exception {
    assertEquals( "onExtra2", dialog.getOndialogextra2() );
  }

  @Test
  public void testGetButtonalign() throws Exception {
    assertEquals( "end", dialog.getButtonalign() );
    dialog.setButtonalign( "start" );
    assertEquals( "start", dialog.getButtonalign() );
  }

  @Ignore
  @Test
  public void testIsHidden() throws Exception {
    Thread thrd = new Thread() {
      public void run() {
        dialog.show();
      }
    };
    thrd.start();
    while ( dialog.isHidden() == true ) {
      Thread.sleep( 300 );
    }
    dialog.hide();
    System.out.println( "dialog.isHidden()" );
    assertTrue( dialog.isHidden() );
  }

  @Ignore
  @Test
  public void testShow() throws Exception {
    Thread thrd = new Thread() {
      public void run() {
        dialog.show();
      }
    };
    thrd.start();
    int tries = 10;
    while ( dialog.isHidden() == true ) {
      tries--;
      if ( tries == 0 ) {
        break;
      }
      Thread.sleep( 300 );
    }
    assertTrue( !dialog.isHidden() );
  }

  @Ignore
  @Test
  public void testHide() throws Exception {
    Thread thrd = new Thread() {
      public void run() {
        dialog.show();
      }
    };
    thrd.start();
    int tries = 10;
    while ( dialog.isHidden() == true ) {
      tries--;
      if ( tries == 0 ) {
        break;
      }
      Thread.sleep( 300 );
    }
    assertTrue( !dialog.isHidden() );
    dialog.hide();
    assertTrue( dialog.isHidden() );
  }

  @Ignore
  @Test
  public void testSetVisibility() throws Exception {
    Thread thrd = new Thread() {
      public void run() {
        dialog.setVisible( true );
      }
    };
    thrd.start();
    int tries = 10;
    while ( dialog.isHidden() == true ) {
      tries--;
      if ( tries == 0 ) {
        break;
      }
      Thread.sleep( 300 );
    }
    assertTrue( !dialog.isHidden() );
    dialog.setVisible( false );
    assertTrue( dialog.isHidden() );
  }

  @Ignore
  @Test
  public void testDialogHeaderDescription() {
    XulDialogheader header = (XulDialogheader) dialog.getElementById( "header" );
    assertEquals( "My preferences", header.getDescription() );
    header.setDescription( "Testing..." );
    assertEquals( "Testing...", header.getDescription() );

  }

  @Ignore
  @Test
  public void testDialogHeaderTitle() {
    XulDialogheader header = (XulDialogheader) dialog.getElementById( "header" );
    assertEquals( "Options", header.getTitle() );
    header.setTitle( "Testing..." );
    assertEquals( "Testing...", header.getTitle() );
  }
}
