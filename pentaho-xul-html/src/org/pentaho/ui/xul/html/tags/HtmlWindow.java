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
