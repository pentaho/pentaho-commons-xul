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
