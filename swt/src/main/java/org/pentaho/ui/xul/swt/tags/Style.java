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
 * Copyright (c) 2002-2019 Hitachi Vantara..  All rights reserved.
 */
package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;

import java.util.Arrays;

public class Style {
  public static int getOverflowProperty( String style ) {
    if ( style == null ) {
      return SWT.NONE;
    }
    //Search for the overflow:auto property
    long count = Arrays.stream( style.split( ";" ) )
      .map( x -> x.toLowerCase().replace( " ", "" ).split( ":" ) )
      .filter( y -> y[ 0 ].equals( "overflow" ) && y[ 1 ].equals( "auto" ) )
      .count();
    return count != 0 ? SWT.V_SCROLL | SWT.H_SCROLL : SWT.NONE;
  }
}
