package org.pentaho.ui.xul.swing;

import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class TestEventHandler extends AbstractXulEventHandler{
  
  
  public void showMessage(){
    this.xulDomContainer.close();
  }
  
  public Object getData() {
    return null;
  }
  
  public void setData(Object data) {
  }

}  