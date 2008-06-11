package org.pentaho.ui.xul.test.samples;

import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class MenuEventHandler extends AbstractXulEventHandler{


  public void changeState(){
    System.out.println("ChangeState fired");
  }
  
  public Object getData() {
    return null;
  }

  public void setData(Object data) {
  }
  
}

  