package org.pentaho.ui.xul.html.tags.transmenu;

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
	
		sb.append( "<a id=\"" ) //$NON-NLS-1$
		.append( getId() )
		.append( "\" href=\"" ); //$NON-NLS-1$
		
		if( getChildNodes().size() > 0 ) {
			XulComponent child = getChildNodes().get( 0 );
			if( child instanceof HtmlMenuitem ) {
				sb.append( ((HtmlMenuitem) child).getCommand() );
				sb.append( "\">" ); //$NON-NLS-1$
			} else {
				sb.append( "#\">" ); //$NON-NLS-1$
			}
		}
		sb.append( getLabel() )
		.append( "</a>\n" ); //$NON-NLS-1$
		
	}
	
	  public void getScript( StringBuilder sb ) {
		  
		  sb.append( "var menu" ) //$NON-NLS-1$
		  .append( " = ms.addMenu(document.getElementById(\"" ) //$NON-NLS-1$
		  .append( getId() )
		  .append( "\"));\n" ); //$NON-NLS-1$

		  XulComponent child = getChildNodes().get( 0 );
			if( child instanceof HtmlMenupopup ) {
				((HtmlMenupopup) child).getScript(sb);
				sb.append( "menu.onactivate = function() { document.getElementById(\"" ) //$NON-NLS-1$
				.append( getId() )
				.append( "\").className = \"hover\"; };\n" ) //$NON-NLS-1$
				.append( "menu.ondeactivate = function() { document.getElementById(\"" ) //$NON-NLS-1$
				.append( getId() )
				.append( "\").className = \"\"; };\n" ); //$NON-NLS-1$
			} else {
				sb.append( "topLevelMenuItems[topLevelMenuItems.length] = '" ) //$NON-NLS-1$
				.append( getId() )
				.append( "';\n" ); //$NON-NLS-1$
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
		if( getChildNodes().size() > 0 ) {
			XulComponent child = getChildNodes().get( 0 );
			if( child instanceof HtmlMenuitem ) {
				if( !label.equals( ( (HtmlMenuitem) child).getLabel() ) ) {
					this.label = label;
					((HtmlMenuitem) child).setLabel( label );
					return;
				}
			}
		}
		this.label = label;
	}

}
