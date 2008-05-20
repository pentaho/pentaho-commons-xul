package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class SampleMenuHandler extends AbstractXulEventHandler{

  public void open(){
    this.getXulDomContainer().createMessageBox("testing...").open();
  }
  
  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setData(Object data) {
    // TODO Auto-generated method stub
    
  }
}

  