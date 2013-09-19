package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class CloseHandler extends AbstractXulEventHandler{

  public boolean closed1 = false;
  public boolean closed2 = false;
  public boolean closed3 = false;
  
  public void setClose1(){
    closed1 = true;
  }
  
  public void setClose2(){
    closed2 = true;
  }
  
  public void setClose3(){
    closed3 = true;
  }
  
}

  