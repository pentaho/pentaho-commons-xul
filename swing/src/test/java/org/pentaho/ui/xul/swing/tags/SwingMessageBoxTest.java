/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SwingMessageBoxTest {

  static Document doc = null;
  static XulDomContainer container;
  static XulMenu menu;
  static SwingMessageBox msg;

  @BeforeClass
  public static void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/menutest.xul" );

    doc = container.getDocumentRoot();
    menu = (XulMenu) doc.getElementById( "file-menu" );

    msg = (SwingMessageBox) doc.createElement( "messagebox" );

  }

  @AfterClass
  public static void cleanup() {
    SwingUtilities.invokeLater( new Thread( () ->
      Arrays.stream( Window.getWindows() )
        .filter( JDialog.class::isInstance )
        .map( window -> (JDialog) window )
        .filter( dialog ->
          dialog.getContentPane().getComponentCount() == 1
            && dialog.getContentPane().getComponent( 0 ) instanceof JOptionPane )
        .forEach( Window::dispose )
      ) );
  }

  @Test
  public void showMessageTest() {
    msg.setTitle( "Title" );
    msg.setMessage( "Message" );

    assertEquals( "Title", msg.getTitle() );
    assertEquals( "Message", msg.getMessage() );

  }

  @Test
  public void showMessageButtonTest() {
    Object[] btns = new String[] { "foo", "bar" };

    msg.setButtons( btns );

    assertArrayEquals( btns, msg.getButtons() );
  }

  @Test
  public void testShow() throws Exception {
    msg.setTitle( "Title" );
    msg.setMessage( "Message" );

    Thread msgThread = new Thread( msg::open );
    msgThread.start();
    Thread.sleep( 1000 );
    assertTrue( msgThread.isAlive() );
    msgThread.interrupt();
  }

  private boolean err = false;

  @Test
  public void testSetScrollable() throws Exception {
    msg.setTitle( "Title" );
    msg.setScrollable( true );
    msg.setMessage( "Message" );

    Thread msgThread = new Thread( () -> {
      try {
        msg.open();
      } catch ( Exception e ) {
        SwingMessageBoxTest.this.err = true;
      }
    } );

    msgThread.start();
    Thread.sleep( 1000 );
    assertTrue( msgThread.isAlive() );
    assertTrue( !err );
    msgThread.interrupt();
  }
}
