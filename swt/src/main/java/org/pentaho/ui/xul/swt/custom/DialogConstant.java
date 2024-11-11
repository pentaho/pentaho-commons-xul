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


package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.SWT;

public enum DialogConstant {
  OK( SWT.OK ), CANCEL( SWT.CANCEL ), YES( SWT.YES ), NO( SWT.NO );

  private int value;

  private DialogConstant( int val ) {
    this.value = val;
  }

  public int getValue() {
    return value;
  }
}
