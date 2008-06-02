package org.pentaho.ui.xul.swing;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.Before;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;

import static org.junit.Assert.*;

public class CheckboxTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/allTags.xul");

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
  public final void testLabel() {
    XulCheckbox check = (XulCheckbox) container.getDocumentRoot().getElementById("checkbox");
    assertEquals("test", check.getLabel());
  }
  
}
