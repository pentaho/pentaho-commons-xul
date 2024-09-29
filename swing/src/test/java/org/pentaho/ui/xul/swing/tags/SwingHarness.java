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


package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingHarness {

  public static void main( String[] args ) {
    try {

      XulDomContainer container = new SwingXulLoader().loadXul( "documents/alignment.xul" );

      XulRunner runner = new SwingXulRunner();
      runner.addContainer( container );

      runner.initialize();
      runner.start();

    } catch ( Exception e ) {
      System.out.println( e.getMessage() );
      e.printStackTrace( System.out );
    }
  }

}
