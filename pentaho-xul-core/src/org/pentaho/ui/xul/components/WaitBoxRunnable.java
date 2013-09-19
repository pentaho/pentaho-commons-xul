package org.pentaho.ui.xul.components;

public abstract class WaitBoxRunnable implements Runnable{

  protected XulWaitBox waitBox;
  public WaitBoxRunnable(XulWaitBox wait){
    this.waitBox = wait;
  }
  
  public abstract void run();

  public abstract void cancel();
}
