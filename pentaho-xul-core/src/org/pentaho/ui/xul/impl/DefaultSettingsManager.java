/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

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
