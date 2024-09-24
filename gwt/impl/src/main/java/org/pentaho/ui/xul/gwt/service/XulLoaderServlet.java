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

package org.pentaho.ui.xul.gwt.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class XulLoaderServlet extends RemoteServiceServlet implements XulLoaderService {

  protected static final Log logger = LogFactory.getLog( XulLoaderServlet.class );
  private static String rootPath = "";

  public String getXulDocument( String url ) {
    StringBuffer sb = new StringBuffer();
    String path = url;
    Reader reader = null;
    try {

      path = this.getServletContext().getRealPath( url );
      File f = new File( path );
      if ( !f.exists() ) {
        // could be running GWT Hosted Mode
        path = url;

        reader = new InputStreamReader( getClass().getResourceAsStream( XulLoaderServlet.rootPath + path ) );
      } else {
        reader = new FileReader( path );
      }
      if ( reader == null ) {
        throw new FileNotFoundException( "Could not find Xul file: " + path );
      }
      BufferedReader buffReader = new BufferedReader( reader );
      String ln;
      while ( ( ln = buffReader.readLine() ) != null ) {
        sb.append( ln );
      }
    } catch ( IOException e ) {
      logger.error( e );
      System.out.println( e.getMessage() );
      e.printStackTrace();
    }
    return sb.toString();

  }

  public String getXulDocument( String url, String resourceBundleUrl ) {
    return "";
  }

  public Boolean setRootContext( String root ) {
    XulLoaderServlet.rootPath = root;
    return true;
  }
}
