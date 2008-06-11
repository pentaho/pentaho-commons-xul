package org.pentaho.ui.xul.test.samples;

import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class TabboxEventHandler extends AbstractXulEventHandler{

  
  public void setDisabled(Boolean flag, Integer pos){
    ((XulTab) document.getElementById("tab"+(pos+1))).setDisabled(flag);
  }
  
  public Object getData() {
    return null;
  }

  public void setData(Object data) {
      
  }

}

  