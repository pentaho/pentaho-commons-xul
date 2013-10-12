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

package org.pentaho.ui.xul.test.swing;

import static org.junit.Assert.assertNotNull;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingHboxTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulHbox hbox;

  @Before
  public void setUp() throws Exception {

    container = new SwingXulLoader().loadXul( "resource/documents/imageButton.xul" );

    runner = new SwingXulRunner();
    runner.addContainer( container );
    hbox = (XulHbox) container.getDocumentRoot().getElementByXPath( "/window/hbox" );

  }

  @After
  public void tearDown() throws Exception {
    try {
      runner.stop();
    } catch ( Exception e ) {
    }
  }

  @Test
  public void testReplaceChild() throws Exception {
    XulButton btn = (XulButton) hbox.getElementByXPath( "button" );

    XulCheckbox check = (XulCheckbox) hbox.getDocument().createElement( "checkbox" );

    hbox.replaceChild( btn, check );

    XulCheckbox check2 = (XulCheckbox) hbox.getDocument().getElementByXPath( "/window/hbox/checkbox" );

    assertNotNull( check2 );
  }

}
