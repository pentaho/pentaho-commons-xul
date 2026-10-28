/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class ProgressHandler extends AbstractXulEventHandler {

  public void addProgress() {
    XulProgressmeter progressMeter = (XulProgressmeter) document.getElementById( "my-progressmeter" );
    progressMeter.setValue( progressMeter.getValue() + 10 );
  }

  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setData( Object data ) {
    // TODO Auto-generated method stub

  }

}
