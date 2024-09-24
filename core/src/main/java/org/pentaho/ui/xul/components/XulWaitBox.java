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

package org.pentaho.ui.xul.components;

public interface XulWaitBox extends XulProgressmeter {

  public void setRunnable( WaitBoxRunnable runnable );

  public void start();

  public void stop();

  public void setModalParent( Object parent );

  public void setCanCancel( boolean canCancel );

  public void setTitle( String title );

  public String getTitle();

  public void setMessage( String message );

  public String getMessage();

  public void setDialogParent( Object parent );

  public void setCancelLabel( String cancel );
}
