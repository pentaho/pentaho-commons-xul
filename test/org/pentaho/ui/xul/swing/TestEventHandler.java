package org.pentaho.ui.xul.swing;

import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class TestEventHandler extends AbstractXulEventHandler{
  
  
  public void showMessage(){
    this.xulDomContainer.close();
  }
  
  
  @Override
  public Object getData() {
    return null;
  }
  
  @Override
  public void setData(Object data) {
  }

}  