package org.pentaho.ui.xul.test.swing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulFileDialog;
import org.pentaho.ui.xul.components.XulStatusbarpanel;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulStatusbar;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingStatusbarTest {
  Document document = null;
  XulDomContainer container;
  XulMenu menu;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/statusbar.xul");

    document = container.getDocumentRoot();
    
  }
  
  @Test
  public void testStatusbarThere() throws Exception{

    XulStatusbar bar = (XulStatusbar) document.getElementById("bar");
    XulStatusbarpanel panel1 = (XulStatusbarpanel) document.getElementById("imagePanel");
    XulStatusbarpanel panel2 = (XulStatusbarpanel) document.getElementById("rightPanel");
    
    assertNotNull(bar);
    assertNotNull(panel1);
    assertNotNull(panel2);
    
  }
  
  
  @Test
  public void testImage() throws Exception{

    XulStatusbarpanel panel1 = (XulStatusbarpanel) document.getElementById("imagePanel");

    assertEquals("testImage.png", panel1.getImage());
  }
  
  
  @Test
  public void testLabel() throws Exception{

    XulStatusbarpanel panel1 = (XulStatusbarpanel) document.getElementById("rightPanel");

    assertEquals("Right panel", panel1.getLabel());
  }
  
}

  