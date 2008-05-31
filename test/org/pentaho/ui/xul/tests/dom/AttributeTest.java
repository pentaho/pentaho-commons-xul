package org.pentaho.ui.xul.tests.dom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

import static org.junit.Assert.*;

public class AttributeTest {
	@Before
  public void setUp() throws Exception {
    
  }

  @After
  public void tearDown() throws Exception {

  }
  
  @Test
  public final void testAttributeCreation() {
  	Attribute attr = new Attribute("name", "value");
  	
  	assertNotNull(attr);
  	assertTrue(attr.getName().equals("name"));
  	assertTrue(attr.getValue().equals("value"));
  }
}
