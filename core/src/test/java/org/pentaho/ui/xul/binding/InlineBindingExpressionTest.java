/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.ui.xul.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Test;

public class InlineBindingExpressionTest {

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void fullExpression() {
    InlineBindingExpression exp = new InlineBindingExpression( "myXulComponentProperty=myModelProperty" );
    assertEquals( "myModelProperty", exp.getModelAttr() );
    assertEquals( "myXulComponentProperty", exp.getXulCompAttr() );
  }

  @Test
  public void minimalExpression() {
    InlineBindingExpression exp = new InlineBindingExpression( "myModelProperty" );
    assertEquals( InlineBindingExpression.DEFAULT_XUL_COMP_ATTR, exp.getXulCompAttr() );
    assertEquals( "myModelProperty", exp.getModelAttr() );
  }

  @Test
  public void fullExpressionUnwantedWhitespace() {
    InlineBindingExpression exp = new InlineBindingExpression( "   myXulComponentProperty   = myModelProperty " );
    assertEquals( "myModelProperty", exp.getModelAttr() );
    assertEquals( "myXulComponentProperty", exp.getXulCompAttr() );
  }

  @Test
  public void error() {
    try {
      InlineBindingExpression exp =
          new InlineBindingExpression( "   myXulComponentProperty   = myModelProperty = test" );

    } catch ( BindingException e ) {
      return;
    }
    fail( "should have thrown a BindingException" );
  }

}
