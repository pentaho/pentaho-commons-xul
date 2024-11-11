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
