package org.pentaho.ui.xul.test.swing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulFileDialog;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.components.XulFileDialog.SEL_TYPE;
import org.pentaho.ui.xul.components.XulFileDialog.VIEW_TYPE;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingFileDialogTest {

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
  public void showFileOpenTest() throws Exception{
    final XulFileDialog dialog = (XulFileDialog) doc.createElement("filedialog");
    dialog.setModalParent(container.getDocumentRoot().getFirstChild().getManagedObject());
    
    Thread msgThread = new Thread(){
      public void run() {
        dialog.showOpenDialog();
      }
    };
    msgThread.start();
    Thread.sleep(1000);
    
    assertTrue(msgThread.isAlive());
    msgThread.interrupt();
    
  }
  
  @Test
  public void showFileOpenTest2() throws Exception{
    final XulFileDialog dialog = (XulFileDialog) doc.createElement("filedialog");
    dialog.setModalParent(container.getDocumentRoot().getFirstChild().getManagedObject());
    
    Thread msgThread = new Thread(){
      public void run() {
        dialog.showOpenDialog(new File("../"));
      }
    };
    msgThread.start();
    Thread.sleep(1000);
    
    assertTrue(msgThread.isAlive());
    msgThread.interrupt();
    
  }
  
  
  
  @Test
  public void showFileSaveTest() throws Exception{
    final XulFileDialog dialog = (XulFileDialog) doc.createElement("filedialog");
    dialog.setModalParent(container.getDocumentRoot().getFirstChild().getManagedObject());
    
    Thread msgThread = new Thread(){
      public void run() {
        dialog.showSaveDialog();
      }
    };
    msgThread.start();
    Thread.sleep(1000);
    
    assertTrue(msgThread.isAlive());
    msgThread.interrupt();
    
  }
  
  @Test
  public void showFileSaveTest2() throws Exception{
    final XulFileDialog dialog = (XulFileDialog) doc.createElement("filedialog");
    dialog.setModalParent(container.getDocumentRoot().getFirstChild().getManagedObject());
    
    Thread msgThread = new Thread(){
      public void run() {
        dialog.showSaveDialog(new File("../"));
      }
    };
    msgThread.start();
    Thread.sleep(1000);
    
    assertTrue(msgThread.isAlive());
    msgThread.interrupt();
    
  }
  
  @Test
  public void testSelType() throws Exception{
    final XulFileDialog dialog = (XulFileDialog) doc.createElement("filedialog");
    dialog.setSelectionMode(SEL_TYPE.SINGLE);
    assertEquals(SEL_TYPE.SINGLE, dialog.getSelectionMode());
    
    dialog.setSelectionMode(SEL_TYPE.MULTIPLE);
    assertEquals(SEL_TYPE.MULTIPLE, dialog.getSelectionMode());
    
  }
  
  @Test
  public void testViewType() throws Exception{
    final XulFileDialog dialog = (XulFileDialog) doc.createElement("filedialog");
    dialog.setViewType(VIEW_TYPE.DIRECTORIES);
    assertEquals(VIEW_TYPE.DIRECTORIES, dialog.getViewType());

    dialog.setViewType(VIEW_TYPE.FILES_DIRECTORIES);
    assertEquals(VIEW_TYPE.FILES_DIRECTORIES, dialog.getViewType());
    
  }
  
}

  