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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BindingExpression {
  private static final String regEx = "([^=]*)=([^\\.]*)\\.(.*)";

  public String sourceAttr;
  public String target;
  public String targetAttr;

  private BindingExpression() {

  }

  public static BindingExpression parse( String str ) {
    if ( !str.matches( regEx ) ) {
      throw new IllegalArgumentException( "Binding Expression invalid" );
    }
    Pattern pat = Pattern.compile( regEx );

    Matcher m = pat.matcher( str );
    m.find();

    BindingExpression expr = new BindingExpression();
    expr.sourceAttr = m.group( 1 ).trim();
    expr.target = m.group( 2 ).trim();
    expr.targetAttr = m.group( 3 ).trim();

    return expr;
  }

}
