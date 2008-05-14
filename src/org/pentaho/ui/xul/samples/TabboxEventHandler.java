package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class TabboxEventHandler extends AbstractXulEventHandler{

  
  public void disable(Integer pos){
    ((XulTab) document.getElementById("tab"+(pos+1))).setDisabled(true);
  }
  
  public Object getData() {
    return null;
  }

  public void setData(Object data) {
      
  }

}

  