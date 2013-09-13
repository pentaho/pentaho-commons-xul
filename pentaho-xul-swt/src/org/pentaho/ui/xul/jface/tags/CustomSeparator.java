package org.pentaho.ui.xul.jface.tags;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;

public class CustomSeparator extends Separator implements IContributionItem {

	private String id;
	
	public void setId( String id ) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
}
