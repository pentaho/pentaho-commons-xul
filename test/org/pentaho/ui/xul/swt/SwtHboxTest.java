package org.pentaho.ui.xul.swt;

import static org.junit.Assert.assertNotNull;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.containers.XulHbox;

public class SwtHboxTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulHbox hbox;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwtXulLoader().loadXul("resource/documents/imageButton.xul");

    runner = new SwtXulRunner();
    runner.addContainer(container);
    hbox = (XulHbox) container.getDocumentRoot().getElementByXPath("/window/hbox");

  }
  
  @After
  public void tearDown() throws Exception {
    try{
      runner.stop();
    } catch(Exception e){}
  }
  
  @Test
  public void testReplaceChild() throws Exception{
    XulButton btn = (XulButton) hbox.getElementByXPath("button");
    
    XulCheckbox check = (XulCheckbox) hbox.getDocument().createElement("checkbox");
    
    hbox.replaceChild(btn, check);
    
    XulCheckbox check2 = (XulCheckbox) hbox.getDocument().getElementByXPath("/window/hbox/checkbox");

    assertNotNull(check2);
  }
  
}
