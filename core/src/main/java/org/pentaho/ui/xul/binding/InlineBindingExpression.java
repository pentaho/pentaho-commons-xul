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

public class InlineBindingExpression {

  private String xulCompAttr;

  private String modelAttr;

  private static final String USAGE =
      "Usage: Inline binding expression syntax: \npen:binding=\"modelProperty\" "
        + "\nor pen:binding=\"xulComponentProperty=modelProperty\"";

  public static final String DEFAULT_XUL_COMP_ATTR = "label";

  public InlineBindingExpression( String exp ) {
    String[] attributes = exp.trim().split( "=" );
    if ( attributes.length < 1 || attributes.length > 2 ) {
      throw new BindingException( "Inline binding expression [" + exp + "] contains an invalid number of attributes.\n"
          + USAGE );
    }

    switch ( attributes.length ) {
      case 1:
        // convention says if you leave off the xul component attribute, label is assumed
        xulCompAttr = DEFAULT_XUL_COMP_ATTR;
        modelAttr = attributes[0].trim();
        break;
      case 2:
        xulCompAttr = attributes[0].trim();
        modelAttr = attributes[1].trim();
        break;

    }
  }

  public String getXulCompAttr() {
    return xulCompAttr;
  }

  public void setXulCompAttr( String xulCompAttr ) {
    this.xulCompAttr = xulCompAttr;
  }

  public String getModelAttr() {
    return modelAttr;
  }

  public void setModelAttr( String modelAttr ) {
    this.modelAttr = modelAttr;
  }

}
