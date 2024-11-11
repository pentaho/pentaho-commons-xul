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
