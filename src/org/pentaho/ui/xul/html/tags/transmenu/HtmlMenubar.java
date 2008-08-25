package org.pentaho.ui.xul.html.tags.transmenu;

import java.util.ArrayList;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.html.IHtmlElement;

public class HtmlMenubar extends HtmlElement implements XulMenubar {

	public HtmlMenubar(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
	    super( tagName );

	  }
	
	  public void getHtml( StringBuilder sb ) {
		  
		  // first add the div
		  sb.append( "<div id=\"" )
		  .append( getId() )
		  .append("\">\n");
		  
		  for( XulComponent component : children ) {
			  if( component instanceof HtmlMenu ) {
				  ((IHtmlElement)component).getHtml(sb);
			  }
		  }

		  sb.append( "</div>\n" );
		  
		  // now add the script
		  getScript( sb );
		  
	  }

	  public void getScript( StringBuilder sb ) {
		  sb.append( "<script language=\"javascript\">\n" )
		  .append( 	"if (TransMenu.isSupported()) {\n" )
		  .append( 		"var topLevelMenuItems = new Array();\n" )
		  .append( 		"var ms = new TransMenuSet(TransMenu.direction.down, 1, 0, TransMenu.reference.bottomLeft);\n" );

		  for( XulComponent component : children ) {
			  if( component instanceof HtmlMenu ) {
				  ((HtmlMenu)component).getScript(sb);
			  }
		  }

		  sb.append( 		"TransMenu.renderAll();\n" );
		  sb.append( 	"}\n" );
		  sb.append( "</script>\n" );

	  }
	  
	public void addComponent(XulComponent c) {
		    super.addComponent(c);
	}

	public void addComponentAt(XulComponent c, int idx) {
		super.addComponentAt(c, idx);
	}

}
