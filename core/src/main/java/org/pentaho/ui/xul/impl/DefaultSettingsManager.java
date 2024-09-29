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


package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulSettingsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class DefaultSettingsManager extends Properties implements XulSettingsManager {

  File propFile;

  public DefaultSettingsManager( File propFile ) throws IOException {
    if ( propFile.exists() == false ) {
      propFile.createNewFile();
    }
    this.propFile = propFile;
    InputStream in;
    in = new FileInputStream( propFile );

    super.load( in );
  }

  public String getSetting( String prop ) {
    return super.getProperty( prop );
  }

  public void storeSetting( String prop, String val ) {
    super.setProperty( prop, val );
  }

  public void save() throws IOException {
    OutputStream out = null;
    try {
      out = new FileOutputStream( propFile );
      super.store( out, "" );
      out.flush();
    } finally {
      out.close();
    }
  }

  /** ==============Implementation Specific methods ======================== **/
}
