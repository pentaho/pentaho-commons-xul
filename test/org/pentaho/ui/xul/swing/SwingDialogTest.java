package org.pentaho.ui.xul.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.swing.JPanel;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.containers.XulDialog;

public class SwingDialogTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulDialog dialog;

  @Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/dialogs.xul");

    runner = new SwingXulRunner();
    runner.addContainer(container);
    dialog = (XulDialog) container.getDocumentRoot().getElementById("dialog");

  }

  @After
  public void tearDown() throws Exception {
    try{
      runner.stop();
    } catch(Exception e){}
  }
  
  @Test
  public void testGetButtons() throws Exception{
    assertEquals("accept,cancel,extra1,extra2",dialog.getButtons());
  }
  
  @Test
  public void testSetButtons() throws Exception{
    dialog.setButtons("accept,cancel");
    assertEquals("accept,cancel",dialog.getButtons());
  }
  
  @Test
  public void testGetButtonlabelcancel() throws Exception{
    assertEquals("Cancel", dialog.getButtonlabelcancel());
  }
  
  @Test
  public void testSetButtonlabelcancel() throws Exception{
    dialog.setButtonlabelcancel("cancel me please");
    assertEquals("cancel me please", dialog.getButtonlabelcancel());
  }
  
  @Test
  public void testGetButtonlabelaccept() throws Exception{
    assertEquals("Save", dialog.getButtonlabelaccept());
  }
  
  @Test
  public void testSetButtonlabelaccept() throws Exception{
    dialog.setButtonlabelaccept("save me please");
    assertEquals("save me please", dialog.getButtonlabelaccept());
  }
  
  @Test
  public void testGetButtonlabelextra1() throws Exception{
    assertEquals("extra1", dialog.getButtonlabelextra1());
  }
  
  @Test
  public void testSetButtonlabelextra1() throws Exception{
    dialog.setButtonlabelextra1("this is extra1");
    assertEquals("this is extra1", dialog.getButtonlabelextra1());
  }
  
  @Test
  public void testGetButtonlabelextra2() throws Exception{
    assertEquals("extra2", dialog.getButtonlabelextra2());
  }
  
  @Test
  public void testSetButtonlabelextra2() throws Exception{
    dialog.setButtonlabelextra2("this is extra2");
    assertEquals("this is extra2", dialog.getButtonlabelextra2());
  }
  
  @Test
  public void testGetOndialogaccept() throws Exception{
    assertEquals("onAccept", dialog.getOndialogaccept());
  }
  
  @Test
  public void testGetOndialogcancel() throws Exception{
    assertEquals("onCancel", dialog.getOndialogcancel());
  }
  
  @Test
  public void testGetOndialogextra1() throws Exception{
    assertEquals("onExtra1", dialog.getOndialogextra1());
  }
  
  @Test
  public void testGetOndialogextra2() throws Exception{
    assertEquals("onExtra2", dialog.getOndialogextra2());
  }
  
  @Test
  public void testGetButtonalign() throws Exception{
    assertEquals("end", dialog.getButtonalign());
  }

  @Test
  public void testSetButtonalign() throws Exception{
    dialog.setButtonalign("start");
    assertEquals("start", dialog.getButtonalign());
  }
  
  @Test
  public void testIsHidden() throws Exception{
    new Thread(){
      public void run() {
        dialog.show();
      }
    }.start();
    while(dialog.isHidden() == true){
      Thread.sleep(300);
    }
    dialog.hide();
    System.out.println("dialog.isHidden()");
    assertTrue(dialog.isHidden());
  }

  @Test
  public void testShow() throws Exception{
    new Thread(){
      public void run() {
        dialog.show();
      }
    }.start();
    int tries = 10;
    while(dialog.isHidden() == true){
      tries--;
      if(tries == 0){
        break;
      }
      Thread.sleep(300);
    }
    assertTrue(!dialog.isHidden());
  }

  @Test
  public void testHide() throws Exception{
    new Thread(){
      public void run() {
        dialog.show();
      }
    }.start();
    int tries = 10;
    while(dialog.isHidden() == true){
      tries--;
      if(tries == 0){
        break;
      }
      Thread.sleep(300);
    }
    assertTrue(!dialog.isHidden());
    dialog.hide();
    assertTrue(dialog.isHidden());
  }
  
  @Test
  public void testSetVisibility() throws Exception{
    new Thread(){
      public void run() {
        dialog.setVisible(true);
      }
    }.start();
    int tries = 10;
    while(dialog.isHidden() == true){
      tries--;
      if(tries == 0){
        break;
      }
      Thread.sleep(300);
    }
    assertTrue(!dialog.isHidden());
    dialog.setVisible(false);
    assertTrue(dialog.isHidden());
  }
  
  @Test
  public void testDialogHeaderDescription(){
    XulDialogheader header = (XulDialogheader) dialog.getElementById("header");
    assertEquals("My preferences", header.getDescription());
    header.setDescription("Testing...");
    assertEquals("Testing...", header.getDescription());
    
  }
  
  @Test
  public void testDialogHeaderTitle(){
    XulDialogheader header = (XulDialogheader) dialog.getElementById("header");
    assertEquals("Options", header.getTitle());
    header.setTitle("Testing...");
    assertEquals("Testing...", header.getTitle());
  }
}
