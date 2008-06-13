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

public class SwingMenuListTest {

  Document doc = null;
  XulDomContainer container;
  XulMenuList list;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwingXulLoader().loadXul("resource/documents/menulist.xul");

    doc = container.getDocumentRoot();
    list = (XulMenuList) doc.getElementById("list");
    
  }
  
  @Test
  public void testGetSelectedItem() throws Exception{
    XulMenuitem item = (XulMenuitem) doc.getElementById("regions");
    
    assertEquals(list.getSelectedItem(), item);
  }

  @Test
  public void testSetSelectedItem() throws Exception{
    XulMenuitem item = (XulMenuitem) doc.getElementById("sales");
    list.setSelectedItem(item);
    assertEquals(list.getSelectedItem(), item);
  }

  @Test
  public void testGetElements() throws Exception{
    assertEquals(3, list.getElements().size());
  }

  @Test
  public void testGetBinding() throws Exception{
    assertEquals("label", list.getBinding());
  }
  @Test
  public void testBindings() throws Exception{
    List<TestClass> items = new ArrayList<TestClass>();
    XulMenuList list2 = (XulMenuList) doc.getElementById("list2");
    
    items.add(new TestClass("foo"));
    items.add(new TestClass("bar"));
    items.add(new TestClass("baz"));
    items.add(new TestClass("bang"));
    list2.setElements(items);
    
    assertEquals(4, list2.getElements().size());
  }
  
  @Test
  public void testGetSelecetdIndex() throws Exception{
    assertEquals(1, list.getSelectedIndex());
  }
  
  @Test
  public void testSetSelecetdIndex() throws Exception{
    XulMenuitem item = (XulMenuitem) doc.getElementById("sales");
    list.setSelectedIndex(0);
    assertEquals(list.getSelectedItem(), item);
  }
  
  
  private class TestClass{
    private String name;
    public TestClass(String name){
      this.name = name;
    }

    public String getName(){
      return name;
    }
    
    public void setName(String name){
      this.name = name;
    }
    
  }
}

  