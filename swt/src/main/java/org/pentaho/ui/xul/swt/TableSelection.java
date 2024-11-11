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


package org.pentaho.ui.xul.swt;

import org.eclipse.swt.SWT;

/**
 * Values used for table and tree selection modes.
 * 
 * @author gmoran
 */
public enum TableSelection {

  SINGLE( SWT.SINGLE | SWT.FULL_SELECTION ), MULTIPLE( SWT.MULTI | SWT.FULL_SELECTION ),

  // TODO: No true equivalent, need to investigate if this behavior is do-able in SWT
  // XUL defines "cell" level selection as:
  // cell: Individual cells can be selected. (This means only a single cell at a time).
  CELL( SWT.SINGLE ), TEXT( SWT.SINGLE ), NONE( SWT.NONE );

  private final int equivalent;

  private TableSelection( int swtEquivalent ) {
    this.equivalent = swtEquivalent;
  }

  public int getSwtStyle() {
    return equivalent;
  }

}
