package org.pentaho.ui.xul.test.swing;

import static org.junit.Assert.*;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulRadio;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingRadioTest {

  XulRunner runner = null;

  Document doc = null;

  XulDomContainer container;

  XulRadio radio1;

  XulRadio radio2;
  XulRadio radio3;

  @Before
  public void setUp() throws Exception {

    container = new SwingXulLoader().loadXul("resource/documents/radioTest.xul");

    runner = new SwingXulRunner();
    runner.addContainer(container);
    radio1 = (XulRadio) container.getDocumentRoot().getElementById("radio1");
    radio2 = (XulRadio) container.getDocumentRoot().getElementById("radio2");
    radio3 = (XulRadio) container.getDocumentRoot().getElementById("radio3");

  }

  @After
  public void tearDown() throws Exception {
    try {
      runner.stop();
    } catch (Exception e) {
    }
  }

  @Test
  public void testIsSelected() throws Exception {
    //test initial value from parse
    assertTrue(radio2.isSelected());
    assertTrue(!radio1.isSelected());
    
  }
  
  @Test
  public void testSetSelected() throws Exception {
    radio1.setSelected(true);
    assertTrue(!radio2.isSelected());
    assertTrue(radio1.isSelected());
  }
  
  @Test
  public void testGetLabel() throws Exception {
    //test initial value from parse
    assertEquals("Test 1", radio1.getLabel());
  }
  
  @Test
  public void testSetLabel() throws Exception {
    radio1.setLabel("testing");
    assertEquals("testing", radio1.getLabel());
  }
  
  @Test
  public void testIsDisabled() throws Exception {
    //test initial value from parse
    assertTrue(radio1.isDisabled() == false);
    assertTrue(radio3.isDisabled());
  }
  
  @Test
  public void testSetDisabled() throws Exception {
    //test initial value from parse
    radio1.setDisabled(true);
    assertTrue(radio1.isDisabled());
  }
}
