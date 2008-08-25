package org.pentaho.ui.xul.html.tags;

import java.util.ArrayList;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.html.IHtmlElement;
import org.pentaho.ui.xul.html.tags.transmenu.HtmlElement;

public class HtmlWindow extends HtmlElement implements XulWindow {

	  public HtmlWindow(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		    super( tagName );
		    children = new ArrayList<XulComponent>();
		    managedObject = children;
	  }

	  
	  public void getHtml( StringBuilder sb ) {
		  
		  sb.append( "<div id=\"" )
		  .append( getId() )
		  .append("\">\n");
		  
		  for( XulComponent component : children ) {
			  if( component instanceof IHtmlElement ) {
				  ((IHtmlElement)component).getHtml(sb);
			  }
		  }

		  sb.append( "</div>\n" );
	  }
	  
	  public void getScript( StringBuilder sb ) {
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
		// TODO Auto-generated method stub
		return null;
	}

	public String getOnload() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOnunload() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getRootObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public XulDomContainer getXulDomContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void invokeLater(Runnable runnable) {
		// TODO Auto-generated method stub

	}

	public void setOnclose(String onclose) {
		// TODO Auto-generated method stub

	}

	public void setOnload(String onload) {
		// TODO Auto-generated method stub

	}

	public void setOnunload(String onunload) {
		// TODO Auto-generated method stub

	}

	public void setTitle(String title) {
		// TODO Auto-generated method stub

	}

	public void setXulDomContainer(XulDomContainer xulDomContainer) {
		// TODO Auto-generated method stub

	}

}
