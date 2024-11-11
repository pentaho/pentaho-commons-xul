/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul;

public class XulDomException extends XulException {

  private static final long serialVersionUID = -3676902604274491856L;

  public XulDomException() {
    super();
  }

  public XulDomException( String message, Throwable e ) {
    super( message, e );
  }

  public XulDomException( Throwable e ) {
    super( e );
  }

  public XulDomException( String str ) {
    super( str );
  }

}
