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

package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StyleTest {

  @Test
  public void understandsOverflowAuto() {
    assertEquals( SWT.V_SCROLL | SWT.H_SCROLL, Style.getOverflowProperty( "overflow:auto;color:red;" ) );
    assertEquals( SWT.NONE, Style.getOverflowProperty( "overflow:other;" ) );
  }
}
