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
