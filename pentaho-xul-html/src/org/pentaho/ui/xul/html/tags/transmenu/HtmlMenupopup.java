package org.pentaho.ui.xul.html.tags.transmenu;

import java.util.ArrayList;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.html.AbstractHtmlContainer;

public class HtmlMenupopup extends AbstractHtmlContainer implements XulMenupopup {

	  public HtmlMenupopup(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		    super("menupopup"); //$NON-NLS-1$
		    children = new ArrayList<XulComponent>();
		    managedObject = children;
	  }

	  
	  public void getHtml( StringBuilder sb ) {
		  // nothing to do here
	  }

	  public void getScript( Map<String,String> properties, StringBuilder sb ) {
		  
		  for( XulComponent child : children ) {
			  if( child instanceof HtmlMenuitem ) {
				  ((HtmlMenuitem) child).getScript( properties, sb);
			  }
			  else if( child instanceof HtmlMenu ) {
          ((HtmlMenu) child).getScript(properties, sb);
        }
		  }
		  
	  }


    public void adoptAttributes(XulComponent component) {
      // TODO Auto-generated method stub
      
    }
	  
}
