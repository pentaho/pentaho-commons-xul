package org.pentaho.ui.xul.test.swing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingMenuItemTest {

  Document doc = null;
  XulDomContainer container;
  XulMenuList list;
  XulMenuitem item;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/menulist.xul");

    doc = container.getDocumentRoot();
    list = (XulMenuList) doc.getElementById("list");
    item = (XulMenuitem) doc.getElementById("regions");
  }
  
  @Test
  public void testGetAccesskey() throws Exception{
    
  }
  
}

  