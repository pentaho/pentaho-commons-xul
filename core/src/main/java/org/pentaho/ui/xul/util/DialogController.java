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


package org.pentaho.ui.xul.util;

/**
 * A controller for a <code>XulDialog</code>.
 * <p>
 * Benefits of capturing these methods in an interface:
 * <ul>
 * <li>Enforces consistency in public method names in controllers that manage a <code>XulDialog</code>.</li>
 * <li>Allows another controller, inside or outside of the <code>XulDomContainer</code> in which this controller
 * lives, to show/hide a dialog without knowing the implementing class.</li>
 * </ul>
 * 
 * @author mlowery
 */
public interface DialogController<T> {
  void showDialog();

  void hideDialog();

  void addDialogListener( final DialogListener<T> listener );

  void removeDialogListener( final DialogListener<T> listener );

  /**
   * Listeners to this dialog are called when the user accepts or cancels the dialog. On accept, the listener is
   * passed in an object of type <code>T</code>.
   */
  interface DialogListener<T> {
    void onDialogAccept( T returnValue );

    void onDialogCancel();

    void onDialogReady();

    void onDialogError( String errorMessage );
  }
}
