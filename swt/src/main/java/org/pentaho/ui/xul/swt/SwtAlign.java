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

package org.pentaho.ui.xul.swt;

import org.eclipse.swt.SWT;

/**
 * Mapping between XUL align attribute values and SWT align property values.
 * 
 * @author gmoran
 * 
 */
public enum SwtAlign {

  START( SWT.BEGINNING ), CENTER( SWT.CENTER ), END( SWT.END ), BASELINE( SWT.BEGINNING ), STRETCH( SWT.FILL );

  private final int equivalent;

  private SwtAlign( int swtEquivalent ) {
    this.equivalent = swtEquivalent;
  }

  public int getSwtAlign() {
    return equivalent;
  }

}
