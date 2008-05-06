package org.pentaho.ui.xul.swing;

import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class ProgressHandler extends AbstractXulEventHandler {

  public void addProgress() {
    XulProgressmeter progressMeter = (XulProgressmeter) document.getElementById("my-progressmeter");
    progressMeter.setValue(progressMeter.getValue() + 10);
  }
  
  @Override
  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setData(Object data) {
    // TODO Auto-generated method stub

  }

}
