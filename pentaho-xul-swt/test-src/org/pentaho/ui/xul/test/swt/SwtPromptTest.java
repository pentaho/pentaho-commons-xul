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
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.util.XulDialogCallback;

public class SwtPromptTest {
  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;

  @Before
  public void setUp() throws Exception {

    // load anything... doesn't matter for this test.
    container = new SwtXulLoader().loadXul( "resource/documents/treeTest.xul" );

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

    XulPromptBox prompt = (XulPromptBox) container.getDocumentRoot().createElement( "promptbox" );
    prompt.setTitle( "Test Title" );
    prompt.setButtons( new String[] { "Accept", "Cancel" } );
    prompt.setMessage( "Enter your data here:" );
    prompt.setValue( "my default value" );
    prompt.addDialogCallback( new XulDialogCallback<String>() {
      public void onClose( XulComponent component, Status status, String value ) {
        System.out.println( "Component: " + component.getName() );
        System.out.println( "Status: " + status.name() );
        System.out.println( "Value: " + value );
      }

      public void onError( XulComponent component, Throwable err ) {
        System.out.println( err.getMessage() );
      }
    } );

    // prompt.open();

  }

}
