package org.pentaho.ui.xul.binding;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;

public class InlineBindingExpressionTest {

  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void fullExpression() {
    InlineBindingExpression exp = new InlineBindingExpression("myXulComponentProperty=myModelProperty");
    assertEquals("myModelProperty", exp.getModelAttr());
    assertEquals("myXulComponentProperty", exp.getXulCompAttr());
  }
  
  @Test
  public void minimalExpression() {
    InlineBindingExpression exp = new InlineBindingExpression("myModelProperty");
    assertEquals(InlineBindingExpression.DEFAULT_XUL_COMP_ATTR, exp.getXulCompAttr());
    assertEquals("myModelProperty", exp.getModelAttr());
  }
  
  @Test
  public void fullExpressionUnwantedWhitespace() {
    InlineBindingExpression exp = new InlineBindingExpression("   myXulComponentProperty   = myModelProperty ");
    assertEquals("myModelProperty", exp.getModelAttr());
    assertEquals("myXulComponentProperty", exp.getXulCompAttr());
  }

}
