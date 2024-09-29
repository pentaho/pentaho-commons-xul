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


package org.pentaho.ui.xul.test.swt;

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
