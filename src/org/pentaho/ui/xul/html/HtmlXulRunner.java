package org.pentaho.ui.xul.html;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.dom.Document;

public class HtmlXulRunner implements XulRunner {

	private List<XulDomContainer> xulDomContainers = new ArrayList<XulDomContainer>();
	
	private StringBuilder sb = new StringBuilder();
	
	public void addContainer(XulDomContainer xulDomContainer) {
		xulDomContainers.add( xulDomContainer );
	}

	public List<XulDomContainer> getXulDomContainers() {
		return xulDomContainers;
	}

	public void initialize() throws XulException {
	}

	public void start() throws XulException {
		// generate the HTML
		StringBuilder sb = new StringBuilder();
		for( XulDomContainer container : xulDomContainers ) {
			Document doc = container.getDocumentRoot();
			XulComponent component = doc.getRootElement();
			if( component instanceof IHtmlElement ) {
				((IHtmlElement) component).getHtml(sb);
			}
		}
		System.out.println( sb );
	}

	public void stop() throws XulException {
	}

	public String getHtml() {
		return sb.toString();
	}
	
}
