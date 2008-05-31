package org.pentaho.ui.xul.swing;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.Before;
import org.pentaho.ui.xul.components.XulButton;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;

import static org.junit.Assert.*;

public class SwingButtonTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
	
	@Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/imageButton.xul");

    runner = new SwingXulRunner();
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
  
  @Test
  public void testButtonGroup(){
  	XulButton button1 = (XulButton) container.getDocumentRoot().getElementById("firstButton");
  	XulButton  button2 = (XulButton) container.getDocumentRoot().getElementById("secondButton");
  	button2.doClick();
  	
  	assertTrue(button2.isSelected());
  }
}
