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


package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulFileDialog;
import org.pentaho.ui.xul.components.XulFileDialog.RETURN_CODE;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

import java.io.File;

public class FileBrowserHandler extends AbstractXulEventHandler {
  public void showSave() {
    try {
      XulTextbox filename = (XulTextbox) document.getElementById( "fileName" );

      XulFileDialog dialog = (XulFileDialog) document.createElement( "filedialog" );
      RETURN_CODE retval = dialog.showSaveDialog();
      if ( retval == RETURN_CODE.OK ) {
        File file = (File) dialog.getFile();
        filename.setValue( file.getName() );
      }

    } catch ( XulException e ) {
      System.out.println( "Error creating file dialog" );
    }

  }
}
