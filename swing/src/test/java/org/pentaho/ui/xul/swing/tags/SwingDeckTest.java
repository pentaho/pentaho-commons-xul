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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import javax.swing.JPanel;

import org.dom4j.Document;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingDeckTest {

  static XulRunner runner = null;
  static Document doc = null;
  static XulDomContainer container;
  static XulDeck deck;

  @BeforeClass
  public static void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/deck_test.xul" );

    runner = new SwingXulRunner();
    runner.addContainer( container );
    deck = (XulDeck) container.getDocumentRoot().getElementById( "deckEle" );

  }

  @AfterClass
  public static void tearDown() throws Exception {
    if ( runner != null ) {
      runner.stop();
    }
  }

  @Test
  public void testGetSelectedIndex() throws Exception {
    assertEquals( deck.getSelectedIndex(), 0 );
  }

  @Test
  public void testSetSelectedIndex() throws Exception {
    assertEquals( deck.getSelectedIndex(), 0 );
    deck.setSelectedIndex( 1 );
    assertEquals( deck.getSelectedIndex(), 1 );
  }

  @Test
  public final void setManagedObject() {
    assertTrue( deck.getManagedObject() instanceof JPanel );
  }

}
