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

package org.pentaho.ui.xul.html.tags.transmenu;

import java.util.List;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.html.AbstractHtmlContainer;

public class HtmlMenu extends AbstractHtmlContainer implements XulMenu {

	private String label;
	
	public HtmlMenu(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
	    super( tagName );

	  }
	
	public void getHtml( StringBuilder sb ) {
	
	  if( getParent() instanceof HtmlMenubar ) {
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
	  } else {
	    // no HTML is needed for sub menus
	  }
		
	}
	
	  public void getScript( Map<String,String> properties, StringBuilder sb ) {
		  
	    String parentJscriptVar = "menu"; //$NON-NLS-1$
	    if( getParent() instanceof HtmlMenubar ) {
	      sb.append( "var menu" ) //$NON-NLS-1$
	      .append( " = ms.addMenu(document.getElementById(\"" ) //$NON-NLS-1$
	      .append( getId() )
	      .append( "\"));\n" ); //$NON-NLS-1$
	    }
	    else if( getParent() instanceof HtmlMenupopup ) {
	      parentJscriptVar = "submenu"; //$NON-NLS-1$
	      HtmlMenupopup parent = (HtmlMenupopup) getParent();
	      // find out the index of this within its siblings
	      List<XulComponent> siblings = parent.getChildNodes();
	      int index = 0;
	      for( XulComponent component: siblings ) {
	        if( component == this ) {
	          // we found ourselves
            sb.append( "menu.addItem(\"" ) //$NON-NLS-1$
            .append( getLabel() )
            .append( "\", \"\" );\n" ); //$NON-NLS-1$
            sb.append( "var submenu" ) //$NON-NLS-1$
            .append( " = menu.addMenu( menu.items[" ) //$NON-NLS-1$
            .append( index )
            .append( "] );\n" ); //$NON-NLS-1$
	        }
	        index++;
	      }
	    }
      XulComponent child = getChildNodes().get( 0 );
      if( child instanceof HtmlMenupopup ) {
        properties.put( "parentJscriptVar", parentJscriptVar ); //$NON-NLS-1$
        ((HtmlMenupopup) child).getScript( properties, sb);
        properties.put( "parentJscriptVar", "menu" ); //$NON-NLS-1$ //$NON-NLS-2$
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

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
