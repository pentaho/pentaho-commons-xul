package org.pentaho.ui.xul.test.dom;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

import static org.junit.Assert.*;

public class ElementDom4JTest {
	Element ele;
  
	@Before
  public void setUp() throws Exception {
    XulDomContainer container = new SwingXulLoader().loadXul("resource/documents/splitterTest.xul");
    ele = container.getDocument(0).getElementById("testContainerElement");
  }

  @After
  public void tearDown() throws Exception {
  	
  }
  
  @Test
  public void testGetName() {
  	assertTrue(ele.getName().equalsIgnoreCase("vbox"));
  }
  
  @Test
  public void testGetDocument(){
  	assertNotNull(ele.getDocument());
  }
  
  @Test
  public void testGetParent(){
  	assertNotNull(ele.getParent().getName().equals("window"));
  }

  @Test
  public void getFirstChild(){
  	assertNotNull(ele.getFirstChild().getName().equals("groupbox"));
  }
  
  @Test
  public void getChildNodes(){
  	List<XulComponent> children = ele.getChildNodes();
  	assertNotNull(children);
  	assertTrue(children.size() == 3);
  }
  
  //TODO: add better test once we have a custom pentaho element
  @Test
  public void getNamespace(){
  	Namespace name = ele.getNamespace();
  	assertNotNull(name);
  }
  
  @Test
  public void setNamespace(){
  	ele.setNamespace("pen", "www.pentaho.com/xul.xml");
  }
  
  @Test
  public void getElementByID(){
  	XulComponent btn = ele.getElementById("button1");
  	assertNotNull(btn);
  	assertTrue(btn.getName().equals("button"));
  }
  
  @Test
  public void getElementByXPath(){
  	XulComponent btn = ele.getElementByXPath("hbox/button");
  	assertNotNull(btn);
  	assertTrue(btn.getName().equals("button"));
  }
  
}
