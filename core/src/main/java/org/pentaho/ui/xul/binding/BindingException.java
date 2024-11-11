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


package org.pentaho.ui.xul.binding;

public class BindingException extends RuntimeException {
  private static final long serialVersionUID = 2465634550238284760L;

  public BindingException( Throwable t ) {
    super( t );
  }

  public BindingException( String message, Throwable cause ) {
    super( message, cause );
  }

  public BindingException( String message ) {
    super( message );
  }
}
