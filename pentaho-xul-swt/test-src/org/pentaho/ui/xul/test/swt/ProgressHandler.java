package org.pentaho.ui.xul.test.swt;

import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class ProgressHandler extends AbstractXulEventHandler {

  public void addProgress() {
    XulProgressmeter progressMeter = (XulProgressmeter) document.getElementById("my-progressmeter");
    progressMeter.setValue(progressMeter.getValue() + 10);
  }

  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setData(Object data) {
    // TODO Auto-generated method stub

  }

}
