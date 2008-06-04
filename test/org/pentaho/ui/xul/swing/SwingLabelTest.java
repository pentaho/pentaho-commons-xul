package org.pentaho.ui.xul.swing;

import static org.junit.Assert.*;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.containers.XulHbox;

public class SwingLabelTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulLabel label;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/labeltest.xul");

    runner = new SwingXulRunner();
    runner.addContainer(container);
    label = (XulLabel) container.getDocumentRoot().getElementById("label-one");
    
  }
  
  @After
  public void tearDown() throws Exception {
    try{
      runner.stop();
    } catch(Exception e){}
  }
  
  @Test
  public void testValue() throws Exception{
    assertEquals("First Label", label.getValue());
    label.setValue("testing...");
    assertEquals("testing...", label.getValue());
    
  }
  
  @Test
  public void testDisable() throws Exception{
    assertTrue(!label.isDisabled());
    label.setDisabled(true);
    assertTrue(label.isDisabled());
    
  }
  
}
