package org.pentaho.ui.xul.test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.binding.DefaultBinding;
import org.pentaho.ui.xul.binding.BindingExpression;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.samples.BindingBean;
import org.pentaho.ui.xul.samples.BindingEventHandler;

public class BindingsTest {

  private XulDomContainer container; 
  private BindingEventHandler handler;
  @Before
  public void setup() throws Exception{

    container = new SwingXulLoader().loadXul("resource/documents/bindingsTest.xul");
    container.initialize();
    handler = (BindingEventHandler) container.getEventHandler("handler");
  }
  @Test
  public void testSimpleBinding() throws Exception{
    handler.test();
    XulLabel firstName = (XulLabel) container.getDocumentRoot().getElementById("firstName");
    assertEquals("Oliver", firstName.getValue());
  }
  
  @Test
  public void testModelBoolean() throws Exception{
    handler.toggleBoolean();
    XulButton button = (XulButton) container.getDocumentRoot().getElementById("disabledButton");
    assertTrue(!button.isDisabled());
    
  }
  
  @Test
  public void testListTreeBind() throws Exception{
    handler.addProduct();
    XulTree table = (XulTree) container.getDocumentRoot().getElementById("productTable");
    
    assertEquals(1, table.getRows());
    handler.addProduct();
    assertEquals(2, table.getRows());
  }
  
  @Test
  public void testConversion() throws Exception{
    XulTextbox degrees = (XulTextbox) container.getDocumentRoot().getElementById("degreesField");
    XulTextbox radians = (XulTextbox) container.getDocumentRoot().getElementById("radiansField");
    degrees.setValue("45");
    assertEquals("0.79", radians.getValue());
    radians.setValue("1.575");
    assertEquals(90, Math.round(Float.parseFloat(degrees.getValue())));
  }
  
  @Test
  public void testExpression() throws Exception{
    BindingExpression exp = BindingExpression.parse("foo=bar.baz");
    assertEquals("foo", exp.sourceAttr);
    assertEquals("bar", exp.target);
    assertEquals("baz", exp.targetAttr);
  }

  @Test
  public void testExpressionFailure() throws Exception{
    try{
      BindingExpression exp = BindingExpression.parse("foo23453.203-baz");
      fail("Should have thrown an IllegalArgumentException");
    } catch(Exception e){
      //You failed properly
    }
  }
  
  @Test
  public void testRemove() throws Exception{
    handler.model.setFirstName("Ted");
    XulLabel firstName = (XulLabel) container.getDocumentRoot().getElementById("firstName");
    assertEquals("Ted", firstName.getValue());
    container.removeBinding(handler.removeMeBinding);
    handler.model.setFirstName("John");
    assertEquals("Ted", firstName.getValue());
  }
}
