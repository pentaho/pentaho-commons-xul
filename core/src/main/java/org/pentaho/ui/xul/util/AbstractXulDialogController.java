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


package org.pentaho.ui.xul.util;

import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;
import org.pentaho.ui.xul.stereotype.Bindable;

import java.util.ArrayList;
import java.util.List;

/**
 * Convenience class that defines common dialog controller functionality. <code>T</code> refers to the type of the
 * value returned when the user "accepts" the dialog (e.g. clicks OK). This type must match the type set on the
 * {@link DialogListener}.
 * 
 * @author mlowery
 */
public abstract class AbstractXulDialogController<T> extends AbstractXulEventHandler implements DialogController<T> {

  // ~ Static fields/initializers
  // ======================================================================================
  // ~ Instance fields
  // =================================================================================================
  private List<DialogListener<T>> listeners = new ArrayList<DialogListener<T>>();

  // ~ Constructors
  // ====================================================================================================

  // ~ Methods
  // =========================================================================================================

  /**
   * Subclasses must override this method to return an instance of <code>XulDialog</code> so that this controller
   * can call <code>show()</code> and <code>hide()</code> on it.
   */
  protected abstract XulDialog getDialog();

  /**
   * Value returned by this method is returned to listeners during <code>onDialogAccept()</code>.
   */
  protected abstract T getDialogResult();

  @Bindable
  public void showDialog() {
    getDialog().show();
  }

  /**
   * Called when the accept button is clicked.
   */
  @Bindable
  public void onDialogAccept() {
    hideDialog();
    for ( DialogListener<T> listener : listeners ) {
      listener.onDialogAccept( getDialogResult() );
    }
    listeners.clear();
  }

  @Bindable
  public void onDialogReady() {
    for ( DialogListener<T> listener : listeners ) {
      listener.onDialogReady();
    }
  }

  /**
   * Called when the cancel button is clicked.
   */
  @Bindable
  public void onDialogCancel() {
    hideDialog();
    for ( DialogListener<T> listener : listeners ) {
      listener.onDialogCancel();
    }
    listeners.clear();
  }

  public void addDialogListener( final DialogListener<T> listener ) {
    listeners.add( listener );
  }

  public void removeDialogListener( final DialogListener<T> listener ) {
    listeners.remove( listener );
  }

  @Bindable
  public void hideDialog() {
    getDialog().hide();
  }

}
