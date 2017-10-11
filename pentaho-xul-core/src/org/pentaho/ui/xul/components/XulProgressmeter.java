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

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * An interface for a XUL progress bar. A progress bar is an element that gives a visual indication of progress
 * completed by advancing a meter or simply displaying an animation.
 * 
 * @author mlowery
 */
public interface XulProgressmeter extends XulComponent {

  String MODE_INDETERMINATE = "undetermined"; //$NON-NLS-1$

  /**
   * An indeterminate progress bar continuously displays animation indicating that an operation of unknown length
   * is occurring.
   * 
   * @param indeterminate
   *          <code>true</code> for indeterminate; <code>false</code> for determinate
   */
  void setIndeterminate( boolean indeterminate );

  /**
   * An indeterminate progress bar continuously displays animation indicating that an operation of unknown length
   * is occurring.
   * 
   * @return indeterminate mode
   */
  boolean isIndeterminate();

  /**
   * The value of this (determinate) progress bar.
   * 
   * @param value
   *          value to set
   */
  void setValue( int value );

  /**
   * The value of this (determinate) progress bar.
   * 
   * @return value of the progress bar
   */
  int getValue();

  /**
   * The minimum value allowed to be set on this progress bar.
   * 
   * @param value
   *          minimum value
   */
  void setMinimum( int value );

  /**
   * The minimum value allowed to be set on this progress bar.
   * 
   * @return minimum value
   */
  int getMinimum();

  /**
   * The maximum value allowed to be set on this progress bar.
   * 
   * @param value
   *          maximum value
   */
  void setMaximum( int value );

  /**
   * The maximum value allowed to be set on this progress bar.
   * 
   * @return maximum value
   */
  int getMaximum();

  /**
   * The mode of this progess bar. One of <code>determined</code> or <code>undetermined</code>.
   * 
   * @param mode
   *          mode to set
   */
  void setMode( String mode );

}
