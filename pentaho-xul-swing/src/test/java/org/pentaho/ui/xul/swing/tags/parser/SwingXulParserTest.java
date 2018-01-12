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

/**
 * 
 */

package org.pentaho.ui.xul.swing.tags.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

import org.dom4j.io.SAXReader;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.impl.XulParser;
import org.pentaho.ui.xul.impl.XulWindowContainer;

/**
 * @author OEM
 * 
 */
public class SwingXulParserTest {
  org.dom4j.Document testDoc;

  XulDomContainer container;

  private XulParser parser;

  @Before
  public void setUp() throws Exception {
    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    DocumentFactory.registerDOMClass( DocumentDom4J.class );
    DocumentFactory.registerElementClass( ElementDom4J.class );
    InputStream in = SwingXulParserTest.class.getClassLoader().getResourceAsStream( "documents/allTags.xul" );
    SAXReader rdr = new SAXReader();
    testDoc = rdr.read( in );

    try {
      parser = new XulParser();
    } catch ( Exception e ) {
      throw new XulException( "Error getting XulParser Instance, probably a DOM Factory problem: " + e.getMessage(), e );
    }

    // attach Renderers
    parser.registerHandler( "WINDOW", "org.pentaho.ui.xul.swing.tags.SwingWindow" );
    parser.registerHandler( "BUTTON", "org.pentaho.ui.xul.swing.tags.SwingButton" );
    parser.registerHandler( "VBOX", "org.pentaho.ui.xul.swing.tags.SwingVbox" );
    parser.registerHandler( "HBOX", "org.pentaho.ui.xul.swing.tags.SwingHbox" );
    parser.registerHandler( "LABEL", "org.pentaho.ui.xul.swing.tags.SwingLabel" );
    parser.registerHandler( "TEXTBOX", "org.pentaho.ui.xul.swing.tags.SwingTextbox" );
    parser.registerHandler( "SCRIPT", "org.pentaho.ui.xul.swing.tags.SwingScript" );
    parser.registerHandler( "SPACER", "org.pentaho.ui.xul.swing.tags.SwingSpacer" );
    parser.registerHandler( "CHECKBOX", "org.pentaho.ui.xul.swing.tags.SwingCheckbox" );
    parser.registerHandler( "GROUPBOX", "org.pentaho.ui.xul.swing.tags.SwingGroupbox" );
    parser.registerHandler( "CAPTION", "org.pentaho.ui.xul.swing.tags.SwingCaption" );
    parser.registerHandler( "LISTBOX", "org.pentaho.ui.xul.swing.tags.SwingListbox" );
    parser.registerHandler( "LISTITEM", "org.pentaho.ui.xul.swing.tags.SwingListitem" );

    final ArrayList<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
    classLoaders.add( getClass().getClassLoader() );

    container = new XulWindowContainer();
    parser.setClassLoaders( classLoaders );
    parser.setContainer( container );
    parser.parseDocument( testDoc.getRootElement() );
  }

  @Test
  public void testRootElement() throws Exception {
    Document root = container.getDocumentRoot();
    assertNotNull( root );
  }

  @Test
  public void testRootIsWindow() throws Exception {
    Document root = container.getDocumentRoot();
    assertTrue( root.getRootElement() instanceof XulWindow );
  }

  @Test
  public void testElementsByXPath() throws Exception {
    Document root = container.getDocumentRoot();
    assertTrue( root.getElementByXPath( "/window" ) != null && root.getElementByXPath( "/window/button" ) != null
        && root.getElementByXPath( "/window/vbox" ) != null && root.getElementByXPath( "/window/vbox/button" ) != null
        && root.getElementByXPath( "/window/label" ) != null && root.getElementByXPath( "/window/hbox" ) != null
        && root.getElementByXPath( "/window/hbox/label" ) != null
        && root.getElementByXPath( "/window/textbox" ) != null && root.getElementByXPath( "/window/script" ) != null
        && root.getElementByXPath( "/window/spacer" ) != null && root.getElementByXPath( "/window/checkbox" ) != null
        && root.getElementByXPath( "/window/groupbox" ) != null
        && root.getElementByXPath( "/window/groupbox/checkbox" ) != null
        && root.getElementByXPath( "/window/caption" ) != null && root.getElementByXPath( "/window/listbox" ) != null
        && root.getElementByXPath( "/window/listbox/listitem" ) != null );
  }

}
