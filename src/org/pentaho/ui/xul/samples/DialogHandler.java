package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class DialogHandler extends AbstractXulEventHandler{

	
	public void showDialog(){
		XulDialog dialog = (XulDialog) document.getElementById("dialog");
		dialog.show();
	}
	
	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(Object data) {
		// TODO Auto-generated method stub
		
	}
	
}
