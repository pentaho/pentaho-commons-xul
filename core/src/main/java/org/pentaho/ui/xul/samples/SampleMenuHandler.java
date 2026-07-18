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



package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class SampleMenuHandler extends AbstractXulEventHandler {

  public void open() {
    try {
      XulMessageBox box = (XulMessageBox) document.createElement( "messagebox" );

      box.setMessage( "testing..." );
      box.open();
    } catch ( XulException e ) {
      System.out.println( "Error creating messagebox " + e.getMessage() );
    }
  }

  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setData( Object data ) {
    // TODO Auto-generated method stub

  }
}
