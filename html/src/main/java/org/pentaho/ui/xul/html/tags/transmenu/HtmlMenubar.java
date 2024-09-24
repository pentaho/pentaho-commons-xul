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

package org.pentaho.ui.xul.html.tags.transmenu;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.html.AbstractHtmlContainer;
import org.pentaho.ui.xul.html.IHtmlElement;

public class HtmlMenubar extends AbstractHtmlContainer implements XulMenubar {

	public HtmlMenubar(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
	    super( tagName );

	  }
	
	  public void getHtml( StringBuilder sb ) {
		  
		  // first add the div
		  sb.append( "<div id=\"" ) //$NON-NLS-1$
		  .append( getId() )
		  .append("\">\n"); //$NON-NLS-1$
		  
		  for( Element component : getChildNodes()) {
			  if( component instanceof HtmlMenu ) {
				  ((IHtmlElement)component).getHtml(sb);
			  }
		  }

		  sb.append( "</div>\n" ); //$NON-NLS-1$
		  
		  // now add the script
		  getScript( new HashMap<String,String>(), sb );
		  
	  }

    public void getScript( StringBuilder sb ) {
      getScript( new HashMap<String,String>(), sb );
    }	  
	  
	  public void getScript( Map<String,String> properties, StringBuilder sb ) {
		  sb.append( "<script language=\"javascript\">\n" ) //$NON-NLS-1$
		  .append( 	"if (TransMenu.isSupported()) {\n" ) //$NON-NLS-1$
		  .append( 		"var topLevelMenuItems = new Array();\n" ) //$NON-NLS-1$
		  .append( 		"var ms = new TransMenuSet(TransMenu.direction.down, 1, 0, TransMenu.reference.bottomLeft);\n" ); //$NON-NLS-1$

		  for( Element component : getChildNodes()) {
			  if( component instanceof HtmlMenu ) {
				  ((HtmlMenu)component).getScript( properties,sb);
			  }
		  }

		  sb.append( 		"TransMenu.renderAll();\n" ); //$NON-NLS-1$
		  sb.append( 	"}\n" ); //$NON-NLS-1$
		  sb.append( "</script>\n" ); //$NON-NLS-1$

	  }


  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
