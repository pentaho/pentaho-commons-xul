package org.pentaho.ui.xul.test.swing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingMenuTest {

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
  public void getAcceltextTest() throws Exception{
    assertEquals("f", menu.getAcceltext());
  }
  
  @Test
  public void getAccesskeyTest() throws Exception{
    assertEquals("F", menu.getAccesskey());
  }
  
  @Test
  public void testDisabled() throws Exception{
    assertTrue(menu.isDisabled() == false);
    menu.setDisabled(true);
    assertTrue(menu.isDisabled());
  }

  @Test
  public void testMenuItemLabel() throws Exception{
    XulMenuitem item = (XulMenuitem) doc.getElementById("paseMenuItem");
    assertEquals("Paste", item.getLabel());
  }

  @Test
  public void testMenuItemGetAccesskey() throws Exception{
    XulMenuitem item = (XulMenuitem) doc.getElementById("paseMenuItem");
    assertEquals("P", item.getAccesskey());
  }

  @Test
  public void testMenuItemDisabled() throws Exception{
    XulMenuitem item = (XulMenuitem) doc.getElementById("paseMenuItem");
    assertTrue(item.isDisabled());
  }

  @Test
  public void testMenuItemGetCommand() throws Exception{
    XulMenuitem item = (XulMenuitem) doc.getElementById("paseMenuItem");
    assertEquals("mainController.paste()", item.getCommand());
  }

  @Test
  public void testMenuItemGetImage() throws Exception{
    XulMenuitem item = (XulMenuitem) doc.getElementById("paseMenuItem");
    assertEquals("foo.png", item.getImage());
  }
  
}

  