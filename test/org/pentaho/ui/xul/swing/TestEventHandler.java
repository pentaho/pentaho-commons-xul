package org.pentaho.ui.xul.swing;

import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMessageBox;

public class TestEventHandler extends XulEventHandler{
  
  
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