package org.pentaho.ui.xul.test;

import org.pentaho.ui.xul.containers.XulDialogAdapter;

public class XulDialogStub extends XulDialogAdapter {
  
  private Object lock = new Object();
  private boolean visible;

  public void hide() {
    synchronized(lock) {
      lock.notify();
    }
  }

  public boolean isHidden() {
    return !visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public void show() {
      synchronized(lock) {
        try {
          lock.wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

  }
}