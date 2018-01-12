/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

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
