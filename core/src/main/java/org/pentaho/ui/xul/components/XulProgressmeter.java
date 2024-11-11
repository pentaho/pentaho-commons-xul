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
