package org.pentaho.ui.xul.test.swing;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.util.Orient;

import static org.junit.Assert.*;
     
public class SwingGroupboxTest {
  
  @Test
  public void testHorizontal() throws Exception{
    InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("resource/documents/groupboxtest.xul");
    assertNotNull("XUL input not found.", in);
    SAXReader rdr = new SAXReader();
    final Document doc = rdr.read(in);
    
    org.pentaho.ui.xul.XulDomContainer container = new SwingXulLoader().loadXul(doc);

    XulGroupbox box = (XulGroupbox )container.getDocumentRoot().getElementById("horizontal-button-box");
    
    assertEquals(Orient.HORIZONTAL, box.getOrientation());
  }
}

  