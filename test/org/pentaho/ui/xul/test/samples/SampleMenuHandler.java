package org.pentaho.ui.xul.test.samples;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class SampleMenuHandler extends AbstractXulEventHandler{

  public void open(){
    try{
      XulMessageBox box = (XulMessageBox) document.createElement("messagebox");
      
      box.setMessage("testing...");
      box.open();
    } catch(XulException e){
      System.out.println("Error creating messagebox "+e.getMessage());
    }
  }
  
  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setData(Object data) {
    // TODO Auto-generated method stub
    
  }
}

  