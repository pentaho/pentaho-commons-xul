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
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.html.HtmlXulLoader;
import org.pentaho.ui.xul.html.IHtmlElement;

public class HtmlHarness {
	  public static void main(String[] args){
		    try{
		      
		      InputStream in = HtmlHarness.class.getClassLoader().getResourceAsStream("documents/menutest2.xul"); //$NON-NLS-1$

		      if(in == null){
//		        System.out.println("Input is null"); //$NON-NLS-1$
		        System.exit(123);
		      }

		      SAXReader rdr = new SAXReader();
		      final Document doc = rdr.read(in);
		      
		      HtmlXulLoader loader = new HtmlXulLoader();
		      
		      // check that get instance works
		      loader = (HtmlXulLoader) loader.getNewInstance();
		      
		      XulDomContainer container = new HtmlXulLoader().loadXul(doc);

		      List<XulComponent> components = container.getDocumentRoot().getElementsByTagName( "menubar" ); //$NON-NLS-1$
		      StringBuilder sb = new StringBuilder();
		      for( XulComponent component : components ) {
					if( component instanceof IHtmlElement ) {
						((IHtmlElement) component).getHtml(sb);
					}
		      }
//		      System.out.println( sb );
/*
		      XulRunner runner = new HtmlXulRunner();
		      runner.addContainer(container);
		      
		      runner.initialize();
		      runner.start();
*/		      
		    } catch(Exception e){
		      e.printStackTrace();
		    }
		  }
}
