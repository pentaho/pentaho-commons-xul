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

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.html.IHtmlElement;
import org.pentaho.ui.xul.impl.AbstractXulComponent;

public class HtmlMenuitem extends AbstractXulComponent implements XulMenuitem, IHtmlElement {

	String command;
	
	String label;
	
	public HtmlMenuitem(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
	    super( tagName );

	  }

	public void getHtml( StringBuilder sb ) {
		// nothing to do
	}
	
	  public void getScript( Map<String,String> properties, StringBuilder sb ) {

	    String parentJscriptVar = properties.get( "parentJscriptVar" ); //$NON-NLS-1$
		  sb.append( parentJscriptVar )
		  .append( ".addItem(\"" ) //$NON-NLS-1$
		  .append( getLabel() )
		  .append( "\", \"" ) //$NON-NLS-1$
		  .append( getCommand() )
		  .append( "\");\n" ); //$NON-NLS-1$

	  }
	

	
	public String getAcceltext() {
		// not supported
		return null;
	}

	public String getAccesskey() {
		// not supported
		return null;
	}

	public String getCommand() {
		return command;
	}

	public String getImage() {
		// not supported
		return null;
	}

	public String getLabel() {
		return label;
	}

	public boolean isDisabled() {
		// not supported
		return false;
	}

	public boolean isSelected() {
		// not supported
		return false;
	}

	public void setAcceltext(String accel) {
		// not supported
	}

	public void setAccesskey(String accessKey) {
		// not supported
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setDisabled(boolean disabled) {
		// not supported
	}

	public void setImage(String image) {
		// not supported
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setSelected(boolean selected) {
		// not supported
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// not supported
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// not supported
	}

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
