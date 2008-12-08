package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class DialogHandler extends AbstractXulEventHandler{

	
	public void showDialog(){
		XulDialog dialog = (XulDialog) document.getElementById("dialog");
		dialog.show();
	}
	
	public void sayHello(){
	  XulTextbox name = (XulTextbox) document.getElementById("nickname");
    XulLabel response = (XulLabel) document.getElementById("helloMsg");
    
    response.setValue("Hello there "+name.getValue());
	}
	
	public void printLoadMessage(String msg){
	  System.out.println("outter handler called: "+msg);
	}
	
	public void showIncludedDialog(){
	  XulDialog dialog = (XulDialog) document.getElementById("dialog");
	  dialog.show();
	}
}
