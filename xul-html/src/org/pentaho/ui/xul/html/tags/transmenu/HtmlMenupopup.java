package org.pentaho.ui.xul.html.tags.transmenu;

import java.util.ArrayList;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;

public class HtmlMenupopup extends HtmlElement implements XulMenupopup {

	  public HtmlMenupopup(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		    super("menupopup"); //$NON-NLS-1$
		    children = new ArrayList<XulComponent>();
		    managedObject = children;
	  }

	  
	  public void getHtml( StringBuilder sb ) {
		  // nothing to do here
	  }

	  public void getScript( StringBuilder sb ) {
		  
		  for( XulComponent child : children ) {
			  if( child instanceof HtmlMenuitem ) {
				  ((HtmlMenuitem) child).getScript(sb);
			  }
		  }
		  
	  }
	  
}
