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


package org.pentaho.ui.xul.test.swt;

import java.awt.GraphicsEnvironment;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.WaitBoxRunnable;
import org.pentaho.ui.xul.components.XulWaitBox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtWaitDialogTest {
  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  Document document;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    // load anything... doesn't matter for this test.
    container = new SwtXulLoader().loadXul( "documents/buttonTester.xul" );
    document = container.getDocumentRoot();
    runner = new SwtXulRunner();
    runner.addContainer( container );
    runner.initialize();

    // Un-comment the following to test the GUI manually
    // runner.initialize();
    // runner.start();
  }

  @Test
  public void promptTest() throws XulException {
    XulWaitBox box;
    box = (XulWaitBox) document.createElement( "waitbox" );
    box.setIndeterminate( true );
    box.setMaximum( 10 );
    box.setCanCancel( true );
    box.setRunnable( new WaitBoxRunnable( box ) {
      boolean canceled = false;

      @Override
      public void run() {
        int steps = 0;
        while ( steps <= 10 ) {
          if ( canceled ) {
            break;
          }
          // this.waitBox.setValue(steps);
          try {
            Thread.sleep( 1000 );
          } catch ( InterruptedException e ) {
            waitBox.stop();
          }
          steps++;
        }
        waitBox.stop();
      }

      @Override
      public void cancel() {
        canceled = true;
      }

    } );

  }

}
