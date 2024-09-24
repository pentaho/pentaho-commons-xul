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

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtHarness {

  public static void main( String[] args ) {
    try {

      InputStream in = SwtXulRunner.class.getClassLoader().getResourceAsStream( "documents/menutest.xul" );
      if ( in == null ) {
        System.out.println( "Input is null" );
        System.exit( 123 );
      }

      SAXReader rdr = new SAXReader();
      final Document doc = rdr.read( in );

      XulDomContainer container = new SwtXulLoader().loadXul( doc );

      XulRunner runner = new SwtXulRunner();
      runner.addContainer( container );

      runner.initialize();
      runner.start();

    } catch ( Exception e ) {
      System.out.println( e.getMessage() );
      e.printStackTrace( System.out );
    }
  }

}
