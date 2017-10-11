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
