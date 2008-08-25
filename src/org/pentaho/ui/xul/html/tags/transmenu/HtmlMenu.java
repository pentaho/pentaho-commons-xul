package org.pentaho.ui.xul.html.tags.transmenu;

import java.util.ArrayList;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Element;

public class HtmlMenu extends HtmlElement implements XulMenu {

	private String label;
	
	public HtmlMenu(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
	    super( tagName );

	  }
	
	public void getHtml( StringBuilder sb ) {
	
		sb.append( "<a id=\"" )
		.append( getId() )
		.append( "\" href=\"" );
		
		if( getChildNodes().size() > 0 ) {
			XulComponent child = getChildNodes().get( 0 );
			if( child instanceof HtmlMenuitem ) {
				sb.append( ((HtmlMenuitem) child).getCommand() );
				sb.append( "\">" );
			} else {
				sb.append( "#\">" );
			}
		}
		sb.append( getLabel() )
		.append( "</a>\n" );
		
	}
	
	  public void getScript( StringBuilder sb ) {
		  
		  sb.append( "var menu" )
//		  .append( idx )
		  .append( " = ms.addMenu(document.getElementById(\"" )
		  .append( getId() )
		  .append( "\"));\n" );

		  XulComponent child = getChildNodes().get( 0 );
			if( child instanceof HtmlMenupopup ) {
				((HtmlMenupopup) child).getScript(sb);
				sb.append( "menu.onactivate = function() { document.getElementById(\"" )
				.append( getId() )
				.append( "\").className = \"hover\"; };\n" )
				.append( "menu.ondeactivate = function() { document.getElementById(\"" )
				.append( getId() )
				.append( "\").className = \"\"; };\n" );
			} else {
				sb.append( "topLevelMenuItems[topLevelMenuItems.length] = '" )
				.append( getId() )
				.append( "';\n" );
				}
			
	  }
	
	public String getAcceltext() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAccesskey() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLabel() {
		if( getChildNodes().size() > 0 ) {
			XulComponent child = getChildNodes().get( 0 );
			if( child instanceof HtmlMenuitem ) {
				return ((HtmlMenuitem) child).getLabel();
			}
		}
		return label;
	}

	public void setAcceltext(String accel) {
		// TODO Auto-generated method stub
	}

	public void setAccesskey(String accessKey) {
		// TODO Auto-generated method stub
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
