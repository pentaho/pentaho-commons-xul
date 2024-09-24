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

package org.pentaho.ui.xul.test.html;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.html.HtmlXulLoader;
import org.pentaho.ui.xul.html.tags.HtmlWindow;
import org.pentaho.ui.xul.test.html.HtmlHarness;

import junit.framework.TestCase;

public class WindowTest extends TestCase {

	private HtmlWindow window; // we manipulate this window in each test
	
	private XulDomContainer getWindow() {
		return getContainer( "documents/htmlwindow.xul" ); //$NON-NLS-1$
	}	
	
	private XulDomContainer getContainer( String filePath ) {
	    try{
		      InputStream in = HtmlHarness.class.getClassLoader().getResourceAsStream( filePath );

		      assertNotNull( "Test document input stream is null", in ); //$NON-NLS-1$

		      
		      SAXReader rdr = new SAXReader();
		      final Document doc = rdr.read(in);
		      
		      HtmlXulLoader loader = new HtmlXulLoader();
		      // check that get instance works
		      loader = (HtmlXulLoader) loader.getNewInstance();

		      XulDomContainer container = loader.loadXul(doc);

		      assertNotNull( "Test document could not be read", container ); //$NON-NLS-1$
		      return container;

		    } catch(Exception e){
		      e.printStackTrace();
		    }
		    return null;
	}
	
	private void setupWindow() {
	      XulDomContainer container = getWindow();

	      org.pentaho.ui.xul.dom.Document doc = container.getDocumentRoot();
	      XulComponent tmp = doc.getRootElement();
	      assertTrue( "Window not found", tmp instanceof HtmlWindow ); //$NON-NLS-1$
	      window = (HtmlWindow) tmp;
	      
	}	
	
	public void testWindowHtml() {
    	setupWindow();

    	assertNotNull( window );
    	StringBuilder sb = new StringBuilder();
    	window.getHtml( sb );
    	assertTrue( "<div> expected", sb.toString().startsWith( "<div" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    	
	}

	public void testWindowScript() {
    	setupWindow();

    	assertNotNull( window );
    	StringBuilder sb = new StringBuilder();
    	window.getScript( sb );
    	assertTrue( "no script expected", sb.toString().equals( "" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    	
	}

	public void testUnsupported() {
    	setupWindow();
	
		// these are not yet supported but should not fail
		
		window.open();
		try {
			window.copy();
		} catch ( Exception e ) {
			assertTrue( false );
		}
		try {
			window.copy( null );
		} catch ( Exception e ) {
			assertTrue( false );
		}
		try {
			window.paste();
		} catch ( Exception e ) {
			assertTrue( false );
		}
		try {
			window.cut();
		} catch ( Exception e ) {
			assertTrue( false );
		}
		assertFalse( window.isClosed() );
		window.setAppicon( null );
		assertNull( window.getRootObject() );
		window.invokeLater( null );
		assertNotNull( window.getXulDomContainer() );
		window.close();
		window.setOnload( "onload test" ); //$NON-NLS-1$
		assertEquals( "onload test", window.getOnload() ); //$NON-NLS-1$
		window.setOnclose( "onclose test" ); //$NON-NLS-1$
		assertEquals( "onclose test", window.getOnclose() ); //$NON-NLS-1$
		window.setOnunload( "onunload test" ); //$NON-NLS-1$
		assertEquals( "onunload test", window.getOnunload() ); //$NON-NLS-1$
		
		window.setTitle( "title test" ); //$NON-NLS-1$
		assertEquals( "title test", window.getTitle() ); //$NON-NLS-1$
	}
	
}
