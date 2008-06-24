package org.pentaho.ui.xul.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.test.samples.BindingBean;

public class NonXulBindingsTest {

  @Test
  public void testObjectToObjectForwardBinding() {
    BindingBean a = new BindingBean();
    BindingBean b = new BindingBean();
    Binding binding = new Binding(a, "property1", b, "property1");
    binding.bindForward();
    
    a.setProperty1("abc");
    
    assertEquals("abc", b.getProperty1());
  }
  
  @Test
  public void testObjectToObjectReverseBinding() {
    BindingBean a = new BindingBean();
    BindingBean b = new BindingBean();
    Binding binding = new Binding(a, "property1", b, "property1");
    binding.bindReverse();
    
    a.setProperty1("abc");
    assertNull(b.getProperty1());
    
    b.setProperty1("def");
    assertEquals("def", a.getProperty1());
  }
  
}
