package org.pentaho.ui.xul.html.tags.transmenu;

import java.beans.PropertyChangeListener;

import org.pentaho.ui.xul.html.IHtmlElement;
import org.pentaho.ui.xul.impl.AbstractXulComponent;
import org.pentaho.ui.xul.util.Orient;

public abstract class HtmlElement extends AbstractXulComponent implements IHtmlElement {

	  protected Orient orientation;

	  protected Orient orient = Orient.HORIZONTAL;

	public HtmlElement(String tagName) {
	    super(tagName);
	}

	public abstract void getHtml( StringBuilder sb );
	
	public abstract void getScript( StringBuilder sb );
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub

	}

	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub

	}

	public void setDisabled(boolean disabled) {
		// TODO Auto-generated method stub

	}

	  public void setOrient(String orientation) {
		    this.orientation = Orient.valueOf(orientation.toUpperCase());
		  }

		  public String getOrient() {
		    return orientation.toString();
		  }

		  public Orient getOrientation() {
		    return orientation;
		  }

}
