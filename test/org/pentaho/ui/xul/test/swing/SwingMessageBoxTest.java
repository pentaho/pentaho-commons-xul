package org.pentaho.ui.xul.test.swing;

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

public class SwingMessageBoxTest {

  Document doc = null;
  XulDomContainer container;
  XulMenu menu;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/menutest.xul");

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

  @Test
  public void testShow() throws Exception{

    final XulMessageBox msg = (XulMessageBox) doc.createElement("messagebox");
    msg.setTitle("Title");
    msg.setMessage("Message");
    
    Thread msgThread = new Thread(){
      public void run() {
        msg.open();
      }
    };
    msgThread.start();
    Thread.sleep(1000);
    assertTrue(msgThread.isAlive());
    msgThread.interrupt();
  }

  private boolean err = false;
  @Test
  public void testSetScrollable() throws Exception{

    final XulMessageBox msg = (XulMessageBox) doc.createElement("messagebox");
    msg.setTitle("Title");
    msg.setScrollable(true);
    msg.setMessage("Message");
    
    Thread msgThread = new Thread(){
      public void run() {
        try{
          msg.open();
        } catch(Exception e){
          SwingMessageBoxTest.this.err = true;
        }
      }
    };
    
    msgThread.start();
    Thread.sleep(1000);
    assertTrue(msgThread.isAlive());
    assertTrue(err == false);
    msgThread.interrupt();
  }
}