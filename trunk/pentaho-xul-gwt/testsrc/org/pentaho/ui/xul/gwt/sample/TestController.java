package org.pentaho.ui.xul.gwt.sample;

import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class TestController  extends AbstractXulEventHandler {
  public String getName(){
    return "controller";
  }
  
  public void onLoad(){
    XulDialog dialog = (XulDialog) document.getElementById("editFilterDialog");
    dialog.show();
  }

}

  