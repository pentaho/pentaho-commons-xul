package org.pentaho.ui.xul.test.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.swing.JPanel;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingDeckTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulDeck deck;

  @Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/deck_test.xul");

    runner = new SwingXulRunner();
    runner.addContainer(container);
    deck = (XulDeck) container.getDocumentRoot().getElementById("deckEle");

  }

  @After
  public void tearDown() throws Exception {
    try{
      runner.stop();
    } catch(Exception e){}
  }
  
  @Test
  public void testGetSelectedIndex() throws Exception{
    assertEquals(deck.getSelectedIndex(), 0);
  }
  
  @Test
  public void testSetSelectedIndex() throws Exception{
    assertEquals(deck.getSelectedIndex(), 0);
    deck.setSelectedIndex(1);
    assertEquals(deck.getSelectedIndex(), 1);
  }
  
  @Test
  public final void setManagedObject(){
    assertTrue(deck.getManagedObject() instanceof JPanel);
  }
  
}
