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
