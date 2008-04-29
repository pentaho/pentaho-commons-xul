package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingDialogheader extends SwingElement implements XulDialogheader{

	private String description;
	private String title;

	public SwingDialogheader(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("dialogheader");
    managedObject = "empty"; // enclosing containers should not try to attach this as a child
    
  }
	
	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
