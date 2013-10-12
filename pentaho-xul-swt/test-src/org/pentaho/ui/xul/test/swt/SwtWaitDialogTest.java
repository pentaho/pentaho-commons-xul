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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.test.swt;

import org.junit.After;
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

    // load anything... doesn't matter for this test.
    container = new SwtXulLoader().loadXul( "resource/documents/buttonTester.xul" );
    document = container.getDocumentRoot();
    runner = new SwtXulRunner();
    runner.addContainer( container );
    Document document = container.getDocumentRoot();

    // Un-comment the following to test the GUI manually
    // runner.initialize();
    // runner.start();
  }

  @After
  public void tearDown() throws Exception {
    try {
      runner.stop();
    } catch ( Exception e ) {
    }
  }

  @Test
  public void promptTest() throws XulException {
    XulWaitBox box;
    try {
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
      // box.start();
    } catch ( XulException e1 ) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

  }

}
