package org.pentaho.ui.xul.html;

import java.util.Map;

public interface IHtmlElement {

	public void getHtml( StringBuilder sb );
	
	public void getScript( Map<String,String> properties, StringBuilder sb );
	
}
