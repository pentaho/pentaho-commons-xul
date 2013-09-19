package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtButtonTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwtXulLoader().loadXul("resource/documents/imageButton.xul");

    runner = new SwtXulRunner();
    runner.addContainer(container);
    
  }

  @After
  public void tearDown() throws Exception {
    try{
      runner.stop();
    } catch(Exception e){}
  }

  @Test
  public final void testButtonClick() {
    XulButton  button = (XulButton) container.getDocumentRoot().getElementById("plainButton");
    button.doClick();
  }
  
  //@TODO implement button group test
//  @Test
//  public void testButtonGroup(){
//    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById("firstButton");
//    XulButton  button2 = (XulButton) container.getDocumentRoot().getElementById("secondButton");
//    button2.doClick();
//    
//    assertTrue(button2.isSelected());
//  }
//  
  @Test
  public void testSetSelected(){
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById("firstButton");
    
    button1.setSelected("true");
    assertTrue(button1.isSelected());
    button1.setSelected("false");
    assertTrue(!button1.isSelected());
    
    button1.setSelected(true);
    assertTrue(button1.isSelected());
    button1.setSelected(false);
    assertTrue(!button1.isSelected());
    
  }
  
  @Test
  public void testGetImage(){
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById("firstButton");
    assertEquals("testImage.png", button1.getImage());
  }
  

  @Test
  public void testGetLabel(){
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById("firstButton");
    assertEquals("TOP", button1.getLabel());
  }
  
  @Test
  public void testGetDir(){
    XulButton button1 = (XulButton) container.getDocumentRoot().getElementById("firstButton");
    assertEquals("reverse", button1.getDir());
  }
}
