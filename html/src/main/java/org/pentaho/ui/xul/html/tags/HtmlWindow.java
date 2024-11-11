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


package org.pentaho.ui.xul.html.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.html.AbstractHtmlContainer;
import org.pentaho.ui.xul.html.IHtmlElement;
import org.pentaho.ui.xul.html.tags.transmenu.HtmlElement;

public class HtmlWindow extends AbstractHtmlContainer implements XulWindow {

	private XulDomContainer domContainer;
	
	private String title;
	
	private String onLoad;
	
	private String onUnload;
	
	private String onClose;
	
	  public HtmlWindow(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		    super( tagName );
		    setManagedObject("window");
		    this.domContainer = domContainer;
	  }

	  
	  public void getHtml( StringBuilder sb ) {
		  
		  sb.append( "<div id=\"" ) //$NON-NLS-1$
		  .append( getId() )
		  .append("\">\n"); //$NON-NLS-1$
		  
		  for( Element component : getChildNodes()) {
			  if( component instanceof IHtmlElement ) {
				  ((IHtmlElement)component).getHtml(sb);
			  }
		  }

		  sb.append( "</div>\n" ); //$NON-NLS-1$
	  }
	  
    public void getScript( StringBuilder sb ) {
      getScript( new HashMap<String,String>(), sb );
    }
    
    public void getScript( Map<String,String> properties, StringBuilder sb ) {
      // nothing to do
    }
    
	public void close() {
		// TODO Auto-generated method stub

	}

	public void copy() throws XulException {
		// TODO Auto-generated method stub

	}

	public void copy(String content) throws XulException {
		// TODO Auto-generated method stub

	}

	public void cut() throws XulException {
		// TODO Auto-generated method stub

	}

	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void open() {
		// TODO Auto-generated method stub

	}

	public void paste() throws XulException {
		// TODO Auto-generated method stub

	}

	public void setAppicon(String icon) {
		// TODO Auto-generated method stub

	}

	public String getOnclose() {
		return onClose;
	}

	public String getOnload() {
		return onLoad;
	}

	public String getOnunload() {
		return onUnload;
	}

	public Object getRootObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitle() {
		return title;
	}

	public XulDomContainer getXulDomContainer() {
		return this.domContainer;
	}

	public void invokeLater(Runnable runnable) {
		// TODO Auto-generated method stub

	}

	public void setOnclose(String onclose) {
		this.onClose = onclose;
	}

	public void setOnload(String onload) {
		this.onLoad = onload;
	}

	public void setOnunload(String onunload) {
		this.onUnload = onunload;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setXulDomContainer(XulDomContainer xulDomContainer) {
		this.domContainer = xulDomContainer;

	}


  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
