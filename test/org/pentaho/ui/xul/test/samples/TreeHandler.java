package org.pentaho.ui.xul.test.samples;

import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class TreeHandler extends AbstractXulEventHandler{
  public int selectedRow = -1;
  
  public void onSelect(int idx){
    this.selectedRow = idx;
  }

}

  