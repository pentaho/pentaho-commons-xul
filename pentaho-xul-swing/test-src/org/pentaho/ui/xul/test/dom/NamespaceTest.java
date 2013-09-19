package org.pentaho.ui.xul.test.dom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Namespace;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

import static org.junit.Assert.*;

public class NamespaceTest {
	@Before
  public void setUp() throws Exception {
    
  }

  @After
  public void tearDown() throws Exception {

  }
  
  @Test
  public final void testNamespace() {
  	Namespace attr = new Namespace("www.foo.xml", "pre");
  	
  	assertNotNull(attr);
  	assertTrue(attr.getURI().equals("www.foo.xml"));
  	assertTrue(attr.getPrefix().equals("pre"));
  }
}
