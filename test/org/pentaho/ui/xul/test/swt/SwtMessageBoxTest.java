package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swt.SwtXulLoader;

public class SwtMessageBoxTest {

  Document doc = null;
  XulDomContainer container;
  XulMenu menu;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwtXulLoader().loadXul("resource/documents/menutest.xul");

    doc = container.getDocumentRoot();
    menu = (XulMenu) doc.getElementById("file-menu");
    
  }
  
  @Test
  public void showMessageTest() throws Exception{
    final XulMessageBox msg = (XulMessageBox) doc.createElement("messagebox");
    msg.setTitle("Title");
    msg.setMessage("Message");
    
    assertEquals("Title", msg.getTitle());
    assertEquals("Message", msg.getMessage());
    
  }

  @Test
  public void showMessageButtonTest() throws Exception{
    final XulMessageBox msg = (XulMessageBox) doc.createElement("messagebox");

    Object[] btns = new String[]{"foo","bar"};
    
    msg.setButtons(btns);
    
    assertEquals(btns, msg.getButtons());
    
  }
  
}