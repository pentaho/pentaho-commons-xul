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

/**
 * 
 */

package org.pentaho.ui.xul;

/**
 * @author nbaker
 * 
 */
public class XulException extends Exception {
  private static final long serialVersionUID = -4430086632572793141L;

  public XulException() {
    super();
  }

  public XulException( String message, Throwable e ) {
    super( message, e );
  }

  public XulException( Throwable e ) {
    super( e );
  }

  public XulException( String str ) {
    super( str );
  }
}
