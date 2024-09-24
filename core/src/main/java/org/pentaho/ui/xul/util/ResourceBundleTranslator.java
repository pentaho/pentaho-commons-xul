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

package org.pentaho.ui.xul.util;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceBundleTranslator {

  public static String translate( InputStream input, ResourceBundle bundle ) throws IOException {
    BufferedReader reader = new BufferedReader( new InputStreamReader( input ) );

    StringBuffer buf = new StringBuffer();
    String line;
    while ( ( line = reader.readLine() ) != null ) {
      buf.append( line );
    }

    String template = buf.toString();

    Pattern pattern = Pattern.compile( "\\$\\{([^\\}]*)\\}" ); //$NON-NLS-1$

    Matcher m = pattern.matcher( template );
    StringBuffer sb = new StringBuffer();
    while ( m.find() ) {
      m.appendReplacement( sb, ResourceBundleTranslator.getTranslatedValue( m.group( 1 ), bundle ) );
    }
    m.appendTail( sb );

    return sb.toString();
  }

  public static String translate( String input, ResourceBundle bundle ) throws IOException {

    String template = input;

    Pattern pattern = Pattern.compile( "\\$\\{([^\\}]*)\\}" ); //$NON-NLS-1$

    Matcher m = pattern.matcher( template );
    StringBuffer sb = new StringBuffer();
    while ( m.find() ) {
      m.appendReplacement( sb, ResourceBundleTranslator.getTranslatedValue( m.group( 1 ), bundle ) );
    }
    m.appendTail( sb );

    return sb.toString();
  }

  private static String getTranslatedValue( String key, ResourceBundle bundle ) {
    try {
      return StringEscapeUtils.escapeXml( bundle.getString( key ) );
    } catch ( MissingResourceException e ) {
      // return unchanged so later ResourceBundles can have a go at it
      return "\\${" + key + "}"; //$NON-NLS-1$ //$NON-NLS-2$
    }
  }
}
