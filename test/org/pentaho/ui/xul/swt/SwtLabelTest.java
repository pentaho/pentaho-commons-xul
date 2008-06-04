package org.pentaho.ui.xul.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulLabel;

public class SwtLabelTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulLabel label;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwtXulLoader().loadXul("resource/documents/labeltest.xul");

    runner = new SwtXulRunner();
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
